package net.xiaodiclass.xdclass.controller;

import net.xiaodiclass.xdclass.config.WechatConfig;
import net.xiaodiclass.xdclass.domain.JsonData;
import net.xiaodiclass.xdclass.domain.User;
import net.xiaodiclass.xdclass.domain.VideoOrder;
import net.xiaodiclass.xdclass.service.UserService;
import net.xiaodiclass.xdclass.service.VideoOrderService;
import net.xiaodiclass.xdclass.utils.JwtUtils;
import net.xiaodiclass.xdclass.utils.WXPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 拼装微信扫一扫登录url
     *
     * @param accessPage
     * @return
     */
    @GetMapping("/login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page", required = true) String accessPage) throws UnsupportedEncodingException {
        String redirectUrl = wechatConfig.getOpenRedirectUrl();//获取开放平台重定向地址
        String callbackUrl = URLEncoder.encode(redirectUrl, "GBK");//进行编码
        String qrcodeUrl = String.format(wechatConfig.getOpenQrcodeUrl()
                , wechatConfig.getOpenAppid()
                , callbackUrl
                , accessPage);
        return JsonData.buildSuccess(qrcodeUrl);
    }

    /**
     * 微信扫码登录回调地址
     *
     * @param code
     * @param state
     * @param response
     */
    @GetMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(required = true) String code, String state, HttpServletResponse response) throws IOException {
        User user = userService.saveUserService(code);
        //生成jwt
        String token = JwtUtils.geneJsonWebToken(user);
        //state需要拼接 http:// 这样在重定向时才不会站内跳转
        //name需要编码
        response.sendRedirect(state + "?token=" + token + "&head_img=" + user.getHeadImg() + "&name=" + URLEncoder.encode(user.getName(), "UTF-8"));
    }

    @PostMapping("/order/callback")
    public void orderCallback(HttpServletRequest request,HttpServletResponse response) throws Exception {
        InputStream inputStream = request.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        Map<String, String> callbackMap = WXPayUtils.xmlToMap(sb.toString());
        SortedMap<String, String> sortedMap = WXPayUtils.getSortedMap(callbackMap);
        //校验签名是否正确
        if (WXPayUtils.isCorrectSign(sortedMap, wechatConfig.getKey())) {
            //微信支付成功
            if ("SUCCESS".equals(sortedMap.get("result_code"))) {
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);
                //订单初始状态为未支付
                if (dbVideoOrder.getState() == 0) {
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setOutTradeNo(outTradeNo);
                    videoOrder.setNotifyTime(new Date());
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setState(1);
                    int rows = videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
                    if (rows==1) {
                        response.setContentType("text/xml");
                        response.getWriter().println("success");
                        return;
                    }
                }
            }
        }
        //其他情况一律处理失败
        response.setContentType("text/xml");
        response.getWriter().println("fail");
    }
}

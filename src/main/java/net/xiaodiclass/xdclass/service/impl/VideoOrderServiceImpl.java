package net.xiaodiclass.xdclass.service.impl;

import net.xiaodiclass.xdclass.config.WechatConfig;
import net.xiaodiclass.xdclass.domain.User;
import net.xiaodiclass.xdclass.domain.Video;
import net.xiaodiclass.xdclass.domain.VideoOrder;
import net.xiaodiclass.xdclass.dto.VideoOrderDto;
import net.xiaodiclass.xdclass.mapper.UserMapper;
import net.xiaodiclass.xdclass.mapper.VideoMapper;
import net.xiaodiclass.xdclass.mapper.VideoOrderMapper;
import net.xiaodiclass.xdclass.service.VideoOrderService;
import net.xiaodiclass.xdclass.utils.CommonUtils;
import net.xiaodiclass.xdclass.utils.HttpUtils;
import net.xiaodiclass.xdclass.utils.WXPayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private WechatConfig wechatConfig;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String save(VideoOrderDto videoOrderDto) throws Exception {
        //日志打印
        dataLogger.info("module=video_order`api=save`user_id={}`video_id={}",videoOrderDto.getUserId(),videoOrderDto.getVideoId());

        //查找视频信息
        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        //查找用户信息
        User user = userMapper.findById(videoOrderDto.getUserId());

        //生成订单
        VideoOrder videoOrder=new VideoOrder();
        videoOrder.setOpenid(user.getOpenid());
        videoOrder.setOutTradeNo(CommonUtils.GeneratedUUID());
        videoOrder.setState(0);
        videoOrder.setCreateTime(new Date());
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setNickname(user.getName());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setVideoId(videoOrderDto.getVideoId());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setUserId(videoOrderDto.getUserId());
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setDel(0);
        videoOrderMapper.insert(videoOrder);
        //获取codeUrl
        String codeUrl=unifiedOrder(videoOrder);

        return codeUrl;
    }

    @Override
    public int updateVideoOrderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    private String unifiedOrder(VideoOrder videoOrder) throws Exception {
        //int i=1/0;//模拟异常
        //生成签名
        SortedMap<String,String> params= new TreeMap<>();
        params.put("appid",wechatConfig.getAppId());
        params.put("mch_id",wechatConfig.getMchId());
        params.put("nonce_str",CommonUtils.GeneratedUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",wechatConfig.getPayCallbackUrl());
        params.put("trade_type","NATIVE");
        //sign签名
        String sign=WXPayUtils.createSign(params,wechatConfig.getKey());
        params.put("sign",sign);
        String xml=WXPayUtils.mapToXml(params);
        //统一下单
        String orderStr=HttpUtils.doPost(WechatConfig.getUnifiedOrderUrl(),xml,4000);
        if(orderStr==null){
            return null;
        }
        Map<String,String> unifiedOrderMap=WXPayUtils.xmlToMap(orderStr);
        if(unifiedOrderMap!=null){
            return unifiedOrderMap.get("code_url");
        }
        return null;
    }
}

package net.xiaodiclass.xdclass.service.impl;

import net.xiaodiclass.xdclass.config.WechatConfig;
import net.xiaodiclass.xdclass.domain.User;
import net.xiaodiclass.xdclass.mapper.UserMapper;
import net.xiaodiclass.xdclass.service.UserService;
import net.xiaodiclass.xdclass.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveUserService(String code) {
        String accessTokenUrl=String.format(WechatConfig.getOpenAccessTokenUrl(),wechatConfig.getOpenAppid(),wechatConfig.getOpenAppsecret(),code);
        //获取access_token
        Map<String ,Object> baseMap=HttpUtils.doGet(accessTokenUrl);
        if(baseMap==null||baseMap.isEmpty()){ return null;}
        String accessToken = (String) baseMap.get("access_token");
        String openId= (String) baseMap.get("openid");
        User dbUser=userMapper.findByOpenid(openId);
        if(dbUser!=null){//更新用户或者直接返回
            return dbUser;
        }
        //获取用户基本信息
        String userInfoUrl=String.format(WechatConfig.getOpenUserInfoUrl(),accessToken,openId);
        Map<String,Object> baseUserMap=HttpUtils.doGet(userInfoUrl);
        if(baseUserMap==null||baseUserMap.isEmpty()){
            return null;
        }
        String nickname=(String) baseUserMap.get("nickname");
        Double sexTemp=(Double) baseUserMap.get("sex");
        int sex=sexTemp.intValue();
        String province=(String)baseUserMap.get("province");
        String city=(String)baseUserMap.get("city");
        String country=(String)baseUserMap.get("country");
        String headimgurl=(String)baseUserMap.get("headimgurl");
        StringBuilder sb=new StringBuilder(country).append("||").append("province").append("||")
                .append(city);
        String finalAddress=sb.toString();
        //解决乱码
        try {
            nickname=new String(nickname.getBytes("ISO-8859-1"),"UTF-8");
            finalAddress=new String(finalAddress.getBytes("ISO-8859-1"),"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        User user=new User();
        user.setName(nickname);
        user.setSex(sex);
        user.setCity(finalAddress);
        user.setHeadImg(headimgurl);
        user.setOpenid(openId);
        user.setCreateTime(new Date());
        userMapper.save(user);
        return user;
    }
}

package net.xiaodiclass.xdclass.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.xiaodiclass.xdclass.config.WechatConfig;
import net.xiaodiclass.xdclass.domain.Video;
import net.xiaodiclass.xdclass.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private VideoMapper videoMapper;
    @RequestMapping("test")
    public String test(){
    	System.out.println("xdclass.net");
        return "hello xdclass";
    }

    @RequestMapping("test_config")
    public String testConfig(){
        System.out.println(wechatConfig.getAppId());
        return "hello xdclass";
    }

    @RequestMapping("test_db")
    public Object testDb(){
        List<Video> list=videoMapper.finfAll();
        return JSONObject.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm:ss");
    }
}
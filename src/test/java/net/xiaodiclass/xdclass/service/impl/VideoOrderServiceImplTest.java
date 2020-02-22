package net.xiaodiclass.xdclass.service.impl;

import net.xiaodiclass.xdclass.domain.VideoOrder;
import net.xiaodiclass.xdclass.mapper.VideoOrderMapper;
import net.xiaodiclass.xdclass.service.VideoOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class VideoOrderServiceImplTest {
    @Autowired
    private VideoOrderService videoOrderService;

    @Test
    void save() {
    }

    @Test
    void updateVideoOrderByOutTradeNo() {
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setOutTradeNo("10819a5d4064446889b91ad00da87b31");
        videoOrder.setNotifyTime(new Date());
        videoOrder.setOpenid("11111111111111");
        videoOrder.setState(1);
        int rows = videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
        System.out.println("rows:"+rows);
    }

    @Test
    void findByOutTradeNo() {
    }
}
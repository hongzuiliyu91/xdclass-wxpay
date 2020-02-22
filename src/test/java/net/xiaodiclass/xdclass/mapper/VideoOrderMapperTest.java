package net.xiaodiclass.xdclass.mapper;

import net.xiaodiclass.xdclass.domain.VideoOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//junit5中无法使用@RunWith("SpringRunner.class")注解
//改为@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class VideoOrderMapperTest {
    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Test
    void findById() {
        VideoOrder videoOrder=videoOrderMapper.findById(1);
        assertNotNull(videoOrder);
    }

    @Test
    void findByOutTradeNo() {
        VideoOrder videoOrder=videoOrderMapper.findByOutTradeNo("3432");
        assertNotNull(videoOrder);
    }

    @Test
    void insert() {
        VideoOrder videoOrder=new VideoOrder();
        videoOrder.setNickname("fengxi");
        videoOrder.setDel(0);
        videoOrder.setVideoTitle("springboot高级教程");
        videoOrderMapper.insert(videoOrder);
        assertNotNull(videoOrder.getId());
    }

    @Test
    void del() {
        videoOrderMapper.del(2,2);

    }

    @Test
    void findMyOrderList() {
        List<VideoOrder> list=videoOrderMapper.findMyOrderList(3);
        assertNotNull(list);
        for(VideoOrder videoOrder:list){
            System.out.println(videoOrder.getVideoTitle());
        }
    }

    @Test
    void updateVideoOrderByOutTradeNo() {
        VideoOrder videoOrder=new VideoOrder();
        videoOrder.setOutTradeNo("3432");
        videoOrder.setNotifyTime(new Date());
        int i=videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
        assertNotEquals(i,0);
    }
}
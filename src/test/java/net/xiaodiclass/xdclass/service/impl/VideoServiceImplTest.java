package net.xiaodiclass.xdclass.service.impl;

import net.xiaodiclass.xdclass.domain.Video;
import net.xiaodiclass.xdclass.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({SpringExtension.class})
@SpringBootTest
class VideoServiceImplTest {
    @Autowired
    private VideoService videoService;

    @Test
    void findAll() {
        List<Video> list=videoService.findAll();
        assertNotNull(list);
    }

    @Test
    void findById() {
        Video video=videoService.findById(2);
        assertNotNull(video);
    }

    @Test
    void update() {
        Video video=new Video();
        video.setTitle("更新单元测试");
        video.setId(1);
        int i=videoService.update(video);
        assertNotEquals(i,0);
    }

    @Test
    void delete() {
        int i=videoService.delete(13);
        assertNotEquals(i,0);
    }

    @Test
    void save() {
        Video video=new Video();
        video.setTitle("新增单元测试");
        int i=videoService.save(video);
        assertNotNull(video.getId());
    }
}
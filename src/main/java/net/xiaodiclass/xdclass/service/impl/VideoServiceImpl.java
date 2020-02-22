package net.xiaodiclass.xdclass.service.impl;

import net.xiaodiclass.xdclass.domain.Video;
import net.xiaodiclass.xdclass.mapper.VideoMapper;
import net.xiaodiclass.xdclass.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Override
    public List<Video> findAll() {
        return videoMapper.finfAll();
    }

    @Override
    public Video findById(int videoId) {
        return videoMapper.findById(videoId);
    }

    @Override
    public int update(Video video) {
        return videoMapper.update(video);
    }

    @Override
    public int delete(int videoId) {
        return videoMapper.delete(videoId);
    }

    @Override
    public int save(Video video) {
        return videoMapper.save(video);
    }
}

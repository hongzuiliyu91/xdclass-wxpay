package net.xiaodiclass.xdclass.service;

import net.xiaodiclass.xdclass.domain.Video;

import java.util.List;

public interface VideoService {
    List<Video> findAll();
    Video findById(int videoId);
    int update(Video video);
    int delete(int videoId);
    int save(Video video);
}

package net.xiaodiclass.xdclass.controller.admin;

import net.xiaodiclass.xdclass.domain.Video;
import net.xiaodiclass.xdclass.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/api/v1/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;
    /**
     * 根据id更新视频
     * @param video
     * @return
     */
    @PutMapping("update")
    public  int update(@RequestBody Video video){
        return videoService.update(video);
    }

    /**
     * 根据id删除视频
     * @param videoId
     * @return
     */
    @PutMapping("delete")
    public int delete(@RequestParam(value = "video_id",required = true) int videoId){
        return videoService.delete(videoId);
    }

    /**
     * 保存视频对象
     * @param video
     * @return
     */
    @PostMapping("save")
    public int save(@RequestBody Video video){
        int i=videoService.save(video);
        System.out.println(video.getId());
        return i;
    }

}

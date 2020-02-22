package net.xiaodiclass.xdclass.mapper;

import net.xiaodiclass.xdclass.domain.Video;
import net.xiaodiclass.xdclass.provider.VideoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * video数据访问层
 */
public interface VideoMapper {
    @Select("select * from video")
    List<Video> finfAll();

    @Select("select * from video where id=#{id}")
    Video findById(int videoId);

    @UpdateProvider(type = VideoProvider.class,method = "updateVideo")
    int update(Video video);

    @Update("update video set is_delete=1 where id=#{videoId}")
    int delete(int videoId);

    @Insert("INSERT INTO `xdclass`.`video`(`title`, `summary`," +
            " `cover_img`, `view_num`, `price`, `create_time`, `online`," +
            " `point`) VALUES (#{title}," +
            " #{summary}," +
            " #{coverImg}, #{viewNum}, #{price}, #{createTime}, #{online}, #{point});")
    @Options(useGeneratedKeys = true)
    int save(Video video);

}

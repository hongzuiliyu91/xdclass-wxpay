package net.xiaodiclass.xdclass.mapper;

import net.xiaodiclass.xdclass.domain.VideoOrder;
import net.xiaodiclass.xdclass.provider.VideoOrderProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单dao层
 */
public interface VideoOrderMapper {
    /**
     * 根据主键id查找订单
     * @param orderId
     * @return
     */
    @Select("select * from video_order where id=#{id} and del=0")
    VideoOrder findById(@Param("id") int orderId);

    /**
     * 根据订单流水号查找订单
     * @param outTradeNo
     * @return
     */
    @Select("select * from video_order where out_trade_no=#{out_trade_no} and del=0")
    VideoOrder findByOutTradeNo(@Param("out_trade_no")String outTradeNo);

    /**
     * 保存订单，返回包含主键
     * @param videoOrder
     * @return
     */
    @Insert("INSERT INTO `xdclass`.`video_order`" +
            "(`openid`, `out_trade_no`, `state`, `create_time`," +
            " `notify_time`, `total_fee`, `nickname`, `head_img`, `video_id`, " +
            "`video_title`, `video_img`, `user_id`, `ip`, `del`) VALUES" +
            " (#{openid},#{outTradeNo},#{state},#{createTime},#{notifyTime}" +
            ",#{totalFee},#{nickname},#{headImg},#{videoId},#{videoTitle}," +
            "#{videoImg},#{userId},#{ip},#{del});")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int insert(VideoOrder videoOrder);

    /**
     * 逻辑删除订单
     * @param id
     * @param userId
     * @return
     */
    @Update("update video_order set del=1 where id=#{id} and user_id=#{user_id}")
    int del(@Param("id")int id,@Param("user_id")int userId);

    /**
     * 查找我的全部订单
     * @param userId
     * @return
     */
    @Select("select * from video_order where user_id=#{user_id}")
    List<VideoOrder> findMyOrderList(@Param("user_id") int userId);


    /**
     * 根据订单流水号更新
     * @param videoOrder
     * @return
     */
    @UpdateProvider(type = VideoOrderProvider.class,method = "updateVideoOrder")
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);

}
package net.xiaodiclass.xdclass.service;

import net.xiaodiclass.xdclass.domain.VideoOrder;
import net.xiaodiclass.xdclass.dto.VideoOrderDto;
import net.xiaodiclass.xdclass.provider.VideoOrderProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

public interface VideoOrderService {
    /**
     * 下单接口
     * @param videoOrderDto
     * @return
     */
    String save(VideoOrderDto videoOrderDto) throws Exception;

    /**
     * 根据订单流水号更新
     * @param videoOrder
     * @return
     */
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);

    /**
     * 根据订单流水号查找订单
     * @param outTradeNo
     * @return
     */
    VideoOrder findByOutTradeNo(@Param("out_trade_no")String outTradeNo);
}

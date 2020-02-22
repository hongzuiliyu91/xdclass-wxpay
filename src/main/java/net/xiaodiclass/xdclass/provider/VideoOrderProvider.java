package net.xiaodiclass.xdclass.provider;

import net.xiaodiclass.xdclass.domain.VideoOrder;
import org.apache.ibatis.jdbc.SQL;

public class VideoOrderProvider {
    public String updateVideoOrder(final VideoOrder videoOrder){
        return new SQL(){{
            UPDATE("video_order");
            if(videoOrder.getState()!=null){
                SET("state=#{state}");
            }
            if(videoOrder.getNotifyTime()!=null){
                SET("notify_time=#{notifyTime}");
            }
            if(videoOrder.getOpenid()!=null){
                SET("openid=#{openid}");
            }
            WHERE("out_trade_no=#{outTradeNo} and del=0 and state=0");
        }}.toString();
    }

}

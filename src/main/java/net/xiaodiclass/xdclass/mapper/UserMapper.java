package net.xiaodiclass.xdclass.mapper;

import net.xiaodiclass.xdclass.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 */
public interface UserMapper {
    /**
     * 根据主键id查找
     * @param userId
     * @return
     */
    @Select("select * from user where id=#{id}")
    User findById(@Param("id")int userId);

    /**
     * 根据openid查找
     * @param openid
     * @return
     */
    @Select("select * from user where openid=#{openid}")
    User findByOpenid(String openid);

    /**
     * 保存新用户
     * @param user
     * @return
     */
    @Insert("INSERT INTO `xdclass`.`user`(`openid`, `name`, `head_img`, `phone`, `sign`, `sex`, `city`, `create_time`)" +
            " VALUES" +
            " (#{openid},#{name},#{headImg},#{phone},#{sign},#{sex},#{city},#{createTime});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int save(User user);
}

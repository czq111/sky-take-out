package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid=#{openid}")
    User queryUserByOpenid(String openid);

    /**
     * 插入用户
     * @param user
     */
    void insert(User user);
}

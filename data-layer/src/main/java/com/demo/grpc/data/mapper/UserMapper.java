package com.demo.grpc.data.mapper;

import com.demo.grpc.data.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Results(id = "userResultMap", value = {
            @Result(property = "userId", column = "user_id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email")
    })
    @Select("SELECT user_id, name, email FROM users WHERE user_id = #{userId}")
    UserEntity findById(@Param("userId") String userId);

    @Results({
            @Result(property = "userId", column = "user_id", id = true),
            @Result(property = "name", column = "name"),
            @Result(property = "email", column = "email")
    })
    @Select("SELECT user_id, name, email FROM users ORDER BY user_id LIMIT #{limit} OFFSET #{offset}")
    List<UserEntity> findAll(@Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT COUNT(*) FROM users")
    int count();
}

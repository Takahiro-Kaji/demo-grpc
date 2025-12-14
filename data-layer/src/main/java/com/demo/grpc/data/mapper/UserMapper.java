package com.demo.grpc.data.mapper;

import com.demo.grpc.data.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

        @Select("""
                        SELECT user_id, name, email
                        FROM users
                        WHERE user_id = #{userId}
                        """)
        UserEntity findById(@Param("userId") String userId);

        @Select("""
                        SELECT user_id, name, email
                        FROM users
                        ORDER BY user_id
                        LIMIT #{limit} OFFSET #{offset}
                        """)
        List<UserEntity> findAll(@Param("limit") int limit, @Param("offset") int offset);

        @Select("""
                        SELECT COUNT(*)
                        FROM users
                        """)
        int count();
}

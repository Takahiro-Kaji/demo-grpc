package com.demo.grpc.data.mapper;

import com.demo.grpc.data.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    UserEntity findById(@Param("userId") String userId);
    List<UserEntity> findAll(@Param("limit") int limit, @Param("offset") int offset);
    int count();
}


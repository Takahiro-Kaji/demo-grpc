package com.demo.grpc.data.service;

import com.demo.grpc.data.entity.UserEntity;
import com.demo.grpc.data.mapper.UserMapper;
import com.demo.grpc.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@GrpcService
@RequiredArgsConstructor
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    
    private final UserMapper userMapper;
    
    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        log.info("getUser called with userId: {}", request.getUserId());
        
        try {
            UserEntity entity = userMapper.findById(request.getUserId());
            
            if (entity == null) {
                responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                        .withDescription("User not found: " + request.getUserId())
                        .asRuntimeException()
                );
                return;
            }
            
            User user = User.newBuilder()
                .setUserId(entity.getUserId())
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .build();
            
            GetUserResponse response = GetUserResponse.newBuilder()
                .setUser(user)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            log.error("Error in getUser", e);
            responseObserver.onError(
                io.grpc.Status.INTERNAL
                    .withDescription("Internal error: " + e.getMessage())
                    .asRuntimeException()
            );
        }
    }
    
    @Override
    public void listUsers(ListUsersRequest request, StreamObserver<ListUsersResponse> responseObserver) {
        log.info("listUsers called with page: {}, pageSize: {}", request.getPage(), request.getPageSize());
        
        try {
            int pageSize = request.getPageSize() > 0 ? request.getPageSize() : 10;
            int page = request.getPage() > 0 ? request.getPage() : 1;
            int offset = (page - 1) * pageSize;
            
            List<UserEntity> entities = userMapper.findAll(pageSize, offset);
            int total = userMapper.count();
            
            List<User> users = entities.stream()
                .map(entity -> User.newBuilder()
                    .setUserId(entity.getUserId())
                    .setName(entity.getName())
                    .setEmail(entity.getEmail())
                    .build())
                .collect(Collectors.toList());
            
            ListUsersResponse response = ListUsersResponse.newBuilder()
                .addAllUsers(users)
                .setTotal(total)
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (Exception e) {
            log.error("Error in listUsers", e);
            responseObserver.onError(
                io.grpc.Status.INTERNAL
                    .withDescription("Internal error: " + e.getMessage())
                    .asRuntimeException()
            );
        }
    }
}


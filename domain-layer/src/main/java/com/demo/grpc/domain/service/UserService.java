package com.demo.grpc.domain.service;

import com.demo.grpc.domain.dto.ListUsersResponseDto;
import com.demo.grpc.domain.dto.UserDto;
import com.demo.grpc.proto.*;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @GrpcClient("data-layer")
    private UserServiceGrpc.UserServiceBlockingStub userServBlockingiceStub;

    public UserDto getUser(String userId) {
        log.info("getUser called with userId: {}", userId);

        try {
            GetUserRequest request = GetUserRequest.newBuilder()
                    .setUserId(userId)
                    .build();

            // 同期通信でgRPC呼び出し（BlockingStubを使用）
            GetUserResponse response = userServBlockingiceStub.getUser(request);

            User user = response.getUser();
            return new UserDto(
                    user.getUserId(),
                    user.getName(),
                    user.getEmail());

        } catch (StatusRuntimeException e) {
            log.error("gRPC error in getUser", e);
            throw new RuntimeException("Failed to get user: " + e.getMessage(), e);
        }
    }

    public ListUsersResponseDto listUsers(int page, int pageSize) {
        log.info("listUsers called with page: {}, pageSize: {}", page, pageSize);

        try {
            ListUsersRequest request = ListUsersRequest.newBuilder()
                    .setPage(page)
                    .setPageSize(pageSize)
                    .build();

            ListUsersResponse response = userServBlockingiceStub.listUsers(request);

            List<UserDto> users = response.getUsersList().stream()
                    .map(user -> new UserDto(
                            user.getUserId(),
                            user.getName(),
                            user.getEmail()))
                    .collect(Collectors.toList());

            return new ListUsersResponseDto(users, response.getTotal());

        } catch (StatusRuntimeException e) {
            log.error("gRPC error in listUsers", e);
            throw new RuntimeException("Failed to list users: " + e.getMessage(), e);
        }
    }
}

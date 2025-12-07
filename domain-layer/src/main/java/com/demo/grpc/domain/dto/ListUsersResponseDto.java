package com.demo.grpc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListUsersResponseDto {
    private List<UserDto> users;
    private int total;
}


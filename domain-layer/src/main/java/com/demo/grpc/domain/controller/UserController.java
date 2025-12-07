package com.demo.grpc.domain.controller;

import com.demo.grpc.domain.dto.ListUsersResponseDto;
import com.demo.grpc.domain.dto.UserDto;
import com.demo.grpc.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        UserDto user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    public ResponseEntity<ListUsersResponseDto> listUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        ListUsersResponseDto response = userService.listUsers(page, pageSize);
        return ResponseEntity.ok(response);
    }
}


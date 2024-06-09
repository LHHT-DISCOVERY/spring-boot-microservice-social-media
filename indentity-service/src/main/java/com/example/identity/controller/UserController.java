package com.example.identity.controller;

import java.util.Collection;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.identity.common.event_tracking.AuditEventType;
import com.example.identity.common.event_tracking.AuditLogger;
import com.example.identity.common.event_tracking.AuditStatus;
import com.example.identity.dto.request.UserCreateRequest;
import com.example.identity.dto.request.UserUpdateRequest;
import com.example.identity.dto.response.ApiResponse;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.mapper.UserMapper;
import com.example.identity.service.impl.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/v1/users")
// DI bằng constructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    UserService userService;

    UserMapper userMapper;

    @PostMapping("/public/create")
    ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest usercreateRequest) {
        ApiResponse<UserResponse> userApiResponse = new ApiResponse<>();
        userApiResponse.setResult(userService.createEntity(usercreateRequest));
        AuditLogger.info(
                "System Create User", AuditEventType.SIGN_UP, AuditStatus.SUCCESS, "Call API /v1/users/public/create");
        return userApiResponse;
    }

    @GetMapping("/list")
    public ApiResponse<Collection<UserResponse>> getListUser() {
        //         get information by username and roles
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("username : {}", authentication.getName());
        authentication.getAuthorities().forEach(s -> LOGGER.info(s.getAuthority()));

        ApiResponse<Collection<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(
                userService.getList().stream().map(userMapper::toUserResponse).toList());
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userMapper.toUserResponse(userService.findById(id)));
        return apiResponse;
    }

    @PostMapping("/delete")
    public ApiResponse<String> deleteUserById(@RequestParam String id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.deleteById(id));
        return apiResponse;
    }

    @PostMapping("/update")
    public ApiResponse<UserResponse> updateUser(
            @RequestParam String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateEntity(id, userUpdateRequest));
        return apiResponse;
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfoByToken());
        return apiResponse;
    }
}

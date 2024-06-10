package com.example.profile.controller;

import com.example.profile.dto.response.ApiResponse;
import com.example.profile.dto.response.UserProfileResponse;
import com.example.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("users/{id}")
    ApiResponse<UserProfileResponse> getUserProfile(@PathVariable(value = "id") String id) {
        ApiResponse<UserProfileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResponse(userProfileService.getProfile(id));
        return apiResponse;
    }

    @GetMapping("users/list")
    ApiResponse<List<UserProfileResponse>> getUsers() {
        ApiResponse<List<UserProfileResponse>> apiResponse = new ApiResponse<List<UserProfileResponse>>();
        apiResponse.setResponse(userProfileService.getUserProfiles());
        return apiResponse;
    }
}

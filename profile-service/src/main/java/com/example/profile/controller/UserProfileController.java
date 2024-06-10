package com.example.profile.controller;

import com.example.profile.dto.request.UserProfileCreateRequest;
import com.example.profile.dto.response.ApiResponse;
import com.example.profile.dto.response.UserProfileResponse;
import com.example.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/create")
    ApiResponse<UserProfileResponse> createUserProfile(@RequestBody UserProfileCreateRequest request) {
        ApiResponse<UserProfileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResponse(userProfileService.createProfile(request));
        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<UserProfileResponse> getUserProfile(@PathVariable(value = "id") String id) {
        ApiResponse<UserProfileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResponse(userProfileService.getProfile(id));
        return apiResponse;
    }

    @PostMapping("/delete/{id}")
    ApiResponse<String> deleteUserProfile(@PathVariable(value = "id") String id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResponse(userProfileService.deleteProfile(id));
        return apiResponse;
    }

    @GetMapping("/list")
    ApiResponse<List<UserProfileResponse>> getUsers() {
        ApiResponse<List<UserProfileResponse>> apiResponse = new ApiResponse<List<UserProfileResponse>>();
        apiResponse.setResponse(userProfileService.getUserProfiles());
        return apiResponse;
    }
}

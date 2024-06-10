package com.example.profile.controller;

import com.example.profile.dto.request.UserProfileCreateRequest;
import com.example.profile.dto.response.ApiResponse;
import com.example.profile.dto.response.UserProfileResponse;
import com.example.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalUserProfileController {
    // this class be created use to internal api among microservices and not public api for clients
    // this mean, clients not using this api in this class
    UserProfileService userProfileService;

    @PostMapping("/internal/create")
    ApiResponse<UserProfileResponse> createUserProfile(@RequestBody UserProfileCreateRequest request) {
        ApiResponse<UserProfileResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResponse(userProfileService.createProfile(request));
        return apiResponse;
    }


    @PostMapping("/internal/delete/{id}")
    ApiResponse<String> deleteUserProfile(@PathVariable(value = "id") String id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResponse(userProfileService.deleteProfile(id));
        return apiResponse;
    }

}

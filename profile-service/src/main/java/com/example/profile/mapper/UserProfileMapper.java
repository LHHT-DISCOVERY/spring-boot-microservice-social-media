package com.example.profile.mapper;

import com.example.profile.dto.request.UserProfileCreateRequest;
import com.example.profile.dto.response.UserProfileResponse;
import com.example.profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile ToUserProfile(UserProfileCreateRequest userProfileRequest);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);
}

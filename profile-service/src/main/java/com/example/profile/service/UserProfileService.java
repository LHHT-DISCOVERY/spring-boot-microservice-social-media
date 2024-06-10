package com.example.profile.service;

import com.example.profile.dto.request.UserProfileCreateRequest;
import com.example.profile.dto.response.UserProfileResponse;
import com.example.profile.entity.UserProfile;
import com.example.profile.exception.ErrorCode;
import com.example.profile.exception.ProfileException;
import com.example.profile.mapper.UserProfileMapper;
import com.example.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(UserProfileCreateRequest request) {
        if (userProfileRepository.existsByFirstNameAndLastName(request.getFirstName(), request.getLastName())) {
            throw new ProfileException(ErrorCode.PROFILE_ALREADY_EXISTS);
        }
        UserProfile userProfile = userProfileMapper.ToUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getProfile(String id) {
        return userProfileMapper.toUserProfileResponse(userProfileRepository.findById(id).orElseThrow(() -> new ProfileException(ErrorCode.PROFILE_DOES_NOT_EXIST)));
    }

    public String deleteProfile(String id) {
        userProfileRepository.deleteById(id);
        return "delete successfully";
    }

    public List<UserProfileResponse> getUserProfiles() {
        return userProfileRepository.findAll().stream().map(userProfileMapper::toUserProfileResponse).toList();
    }

}

package com.example.identity.mapper;

import com.example.identity.dto.request.ProfileCreateRequest;
import com.example.identity.dto.request.UserCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreateRequest toProfileCreateRequest(UserCreateRequest userCreateRequest);
}

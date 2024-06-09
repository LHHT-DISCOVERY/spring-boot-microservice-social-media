package com.example.identity.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.example.identity.dto.request.UserCreateRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.entity.User;
import com.example.identity.exception.AppException;
import com.example.identity.repository.RoleRepository;
import com.example.identity.repository.UserRepository;
import com.example.identity.service.impl.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    UserService userService; // testing itself (service layer)

    @MockBean
    private UserRepository userRepository; // similar UserControllerTest

    @MockBean
    private RoleRepository roleRepository;

    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;
    private LocalDate doB;
    private User user;

    @BeforeEach
    public void initData() {
        doB = LocalDate.of(2001, 10, 20);
        userCreateRequest = UserCreateRequest.builder()
                .username("huutri")
                .firstName("huu")
                .lastName("ly")
                .password("12345678")
                .dob(doB)
                .build();
        userResponse = UserResponse.builder()
                .id("9999")
                .username("huutri")
                .firstName("huu")
                .lastName("ly")
                .dob(doB)
                .build();

        user = User.builder()
                .id("9999")
                .username("huutri")
                .firstName("huu")
                .lastName("ly")
                .dob(doB)
                .build();
    }

    @Test
    public void createUser_validRequest() {
        // GIVEN
        when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(false); // not find -> can save a new entity
        when(roleRepository.findAllById(any())).thenReturn(List.of()); // find role -> can roles -> can save
        when(userRepository.save(any())).thenReturn(user); // can save

        // WHEN
        // using Bean  @Autowired UserService userService; userService to test method in class service
        var response = userService.createEntity(userCreateRequest);

        // THEN
        assertThat(response.getId()).isEqualTo("9999");
        assertThat(response.getUsername()).isEqualTo("huutri");
    }

    @Test
    public void createUser_userExist_fail() {
        // GIVEN
        //        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
        //                .thenReturn(true); // can find -> can't save a new entity
        // because error by existByUsername() method -> no need using two "when" below
        when(roleRepository.findAllById(any())).thenReturn(List.of()); // find role -> can roles -> can save
        //         when(userRepository.save(any())).thenReturn(user);
        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // WHEN
        // because cause by exception -> no need call save()

        // THEN
        // assertThrows will return variable is exception (ErrorCode)
        var exception = Assertions.assertThrows(AppException.class, () -> userService.createEntity(userCreateRequest));

        assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }

    @Test
    public void createUser_NotRoles() {
        // GIVEN
        when(userRepository.existsByUsername(ArgumentMatchers.anyString())).thenReturn(false);
        when(roleRepository.findAllById(any()))
                .thenReturn(List.of()); // not find role but still call method save() in repository
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        // using Bean  @Autowired UserService userService; userService to test method in class service
        var response = userService.createEntity(userCreateRequest);

        // THEN
        assertThat(response.getRoles()).isEqualTo(null);
        assertThat(response.getUsername()).isEqualTo("huutri");
    }

    @Test
    @WithMockUser(username = "tri") // dependency security test pom.xml-> to pass in method using security
    public void getMyInf_valid_success() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfoByToken();

        assertThat(response.getUsername()).isEqualTo("huutri");
        assertThat(response.getId()).isEqualTo("9999");
    }

    @Test
    @WithMockUser(username = "tri")
    public void getMyInf_notExistUser_fail() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(null));

        var exception = Assertions.assertThrows(AppException.class, () -> userService.getMyInfoByToken());
        assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
        assertThat(exception.getErrorCode().getMessage()).isEqualTo("user not found");
    }

    //    note param in report jacoco:
    //    cxty (as little as possible, this mean little : "if, esle if , esle" condition)
    //    and : default cxty is 1 -> if the method has 1 condition "if" in method -> cxty is 2
    //    if cxty is 2 -> to coverage full (100%) -> need write two method unit test
    //    1: check into condition (into and implement in condition "if")
    //    2: check not into condition (not into condition "if")
    //    -> ex: userService.createEntity()
    //      1: into "if" condition, this mean show at GIVEN , "when"..existsByUsername() -> assertThrows.
    //      2: not into "if" condition -> save successful -> assertThat -> compare result.

}

package com.example.identity.controller;

import com.example.identity.dto.request.UserCreateRequest;
import com.example.identity.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc // create request in unit test , this mean help we can create mock request to controller
// config integration test (this mean run thought layers (controller -> service -> repository -> db) that not mock as
// the unit test )
@Testcontainers
public class IntegrationUserControllerTest {
    @Autowired
    private MockMvc mockMvc; // testing itself (controller layer), using call to api, usually using in controller layer

    //    -> 2 container , 1: test container management , 2: container of mysql that we connect to perform testing
    @Container
    static final MySQLContainer<?> MY_SQL_CONTAINER =
            new MySQLContainer<>("mysql:latest"); // start docker, "latest" is version of mysql in docker can change

    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", () -> MY_SQL_CONTAINER.getJdbcUrl());
        dynamicPropertyRegistry.add("spring.datasource.username", () -> MY_SQL_CONTAINER.getUsername());
        dynamicPropertyRegistry.add("spring.datasource.password", () -> MY_SQL_CONTAINER.getPassword());
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        dynamicPropertyRegistry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
        dynamicPropertyRegistry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    // declare data input and output , show in method (param and return data) in controller
    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;
    private LocalDate doB;
    private List<String> roles;

    @BeforeEach
        // fake data before run
    void initData() {
        doB = LocalDate.of(2001, 10, 20);
        roles = List.of("USER");
        userCreateRequest = UserCreateRequest.builder()
                .username("huutri")
                .firstName("huu")
                .lastName("ly")
                .password("12345678")
                .dob(doB)
                .roles(roles)
                .build();
        userResponse = UserResponse.builder()
                .id("9999")
                .username("huutri")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN (data input , output -> request and response) --- created in initData() method above
        ObjectMapper objectMapper = new ObjectMapper();
        // change local date to string -> import in xml -> line 124
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(userCreateRequest);

        // WHEN (this mean test anything -> using test API)
        // THEN (this mean -> expect things -> this mean can response,... )
        // using Bean @Autowired private MockMvc mockMvc,  mockMvc.perform (...) to test method in class controller
        var response = mockMvc.perform(
                        MockMvcRequestBuilders // create request
                                .post("/users/public/create") // URL
                                .contentType(MediaType.APPLICATION_JSON_VALUE) // request type
                                .content(content)) // param request
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk()) // status code
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)) // get result in response to compare
                .andExpect(MockMvcResultMatchers.jsonPath("result.username")
                        .value("huutri")) // expect more value in response from postman;
                .andExpect(MockMvcResultMatchers.jsonPath("result.firstName")
                        .value("huu")); // expect more value in response from postman;
        log.info(
                "result: {}",
                response.andReturn().getResponse().getContentAsString()); // get a response return from api
    }
}

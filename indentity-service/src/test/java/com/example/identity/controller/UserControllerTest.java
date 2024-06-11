package com.example.identity.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.identity.dto.request.UserCreateRequest;
import com.example.identity.dto.response.UserResponse;
import com.example.identity.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc // create request in unit test , this mean help we can create mock request to controller
@TestPropertySource("/test.properties") // read config from test.properties, overriding in .yml -> connect H2 DB
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc; // testing itself (controller layer), using call to api, usually using in controller layer

    @MockBean // mock bean DI in main controller, bypass beans in service layer because only testing in controller layer
    private UserService userService;

    // declare data input and output , show in method (param and return data) in controller
    private UserCreateRequest userCreateRequest;
    private UserResponse userResponse;
    private LocalDate doB;

    @BeforeEach
    // fake data before run
    void initData() {
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
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN (data input , output -> request and response) --- created in initData() method above
        ObjectMapper objectMapper = new ObjectMapper();
        // change local date to string -> import in xml -> line 124
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(userCreateRequest);

        // not call direct through method in service layer (this mean bypass , not call method in createEntity() in
        // service layer) because testing in controller -> mock it
        Mockito.when(userService.createEntity(ArgumentMatchers.any())).thenReturn(userResponse);

        // WHEN (this mean test anything -> using test API)
        // THEN (this mean -> expect things -> this mean can response,... )
        // using Bean @Autowired private MockMvc mockMvc,  mockMvc.perform (...) to test method in class controller
        mockMvc.perform(
                        MockMvcRequestBuilders // create request
                                .post("/users/public/create") // URL
                                .contentType(MediaType.APPLICATION_JSON_VALUE) // request type
                                .content(content)) // param request
                // THEN
                .andExpect(MockMvcResultMatchers.status().isOk()) // status code
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)) // get result in response to compare
                .andExpect(MockMvcResultMatchers.jsonPath("result.id")
                        .value(9999)); // expect more value in response from postman;
    }

    @Test
    void createUser_invalidUsername() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        userCreateRequest.setUsername("tri");
        String content = objectMapper.writeValueAsString(userCreateRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/public/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 5 character"));
    }
}

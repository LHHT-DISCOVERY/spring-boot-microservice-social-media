package com.example.identity.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.identity.dto.request.RoleRequest;
import com.example.identity.dto.response.ApiResponse;
import com.example.identity.dto.response.RoleResponse;
import com.example.identity.service.impl.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @GetMapping("/list")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getList())
                .build();
    }

    @PostMapping("/create")
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        ApiResponse<RoleResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(roleService.createEntity(roleRequest));
        return apiResponse;
    }

    @PostMapping("/delete/{roleName}")
    ApiResponse<Void> deleteRole(@PathVariable(value = "roleName") String roleName) {
        roleService.deleteById(roleName);
        return new ApiResponse<>();
    }
}

package com.example.file.controller;

import com.example.file.dto.reponse.ApiResponse;
import com.example.file.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/media")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {
    FileService fileService;
    @PostMapping("/upload")
    ApiResponse<Object> uploadFileMedia(@RequestParam("fileUpload") MultipartFile file) throws IOException {
        return ApiResponse.<Object>builder().result(fileService.uploadFile(file)).build();
    }
}

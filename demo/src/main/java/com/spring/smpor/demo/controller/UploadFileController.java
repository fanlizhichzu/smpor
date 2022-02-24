package com.spring.smpor.demo.controller;

import com.spring.smpor.common.annotation.FileCheck;
import com.spring.smpor.common.annotation.ResponseResult;
import com.spring.smpor.common.entity.PageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.certpath.OCSPResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2022/1/7 13:33
 */
@RestController
@RequestMapping("/demo/uploadFile")
@ResponseResult
@Slf4j
public class UploadFileController {
    @PostMapping(value = "/upload")
    @FileCheck
    public String getTableList(@RequestParam("file") MultipartFile file) {
        return file.getOriginalFilename();
    }

    @GetMapping(value = "auth")
    public ResponseEntity<String> auth(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        request.setCharacterEncoding("UTF-8");
        Cookie[] cookies = request.getCookies();
        return ResponseEntity.status(HttpStatus.OK).body("{\"code\": 200, \"msg\": \"未授权!\"}");
    }
}

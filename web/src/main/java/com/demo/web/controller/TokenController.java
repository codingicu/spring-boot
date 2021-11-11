package com.demo.web.controller;

import com.demo.web.util.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 项目: spring-boot
 * 时间: 2021/10/27 2:41
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestController
@RequestMapping("token")
public class TokenController {
    @PostMapping("")
    public Object addToken() {
        String token = JwtUtils.createToken("uid");
        return ResponseEntity.status(201).header(HttpHeaders.AUTHORIZATION, token).build();
    }

    @DeleteMapping()
    public Object deleteToken(@RequestHeader String authorization) {
        System.out.println(authorization);
        return null;
    }
}

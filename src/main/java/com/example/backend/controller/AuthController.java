package com.example.backend.controller;

import com.example.backend.common.CommonResp;
import com.example.backend.model.dto.LoginDTO;
import com.example.backend.model.vo.UserLoginVO;
import com.example.backend.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 认证接口
 * @create : 2024-06-14 11:33
 **/

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public CommonResp<UserLoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        return CommonResp.success(authService.userLogin(loginDTO));
    }

    @PostMapping("logout")
    public CommonResp<?> logout() {
        authService.logout();
        return CommonResp.success();
    }
}

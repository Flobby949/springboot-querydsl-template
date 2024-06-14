package com.example.backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 登录请求体
 * @create : 2024-06-14 10:47
 **/

@Data
public class LoginDTO {
    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}

package com.example.backend.model.vo;

import lombok.Data;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 登录返回视图
 * @create : 2024-06-14 10:22
 **/

@Data
public class UserLoginVO {

    private Integer pkId;
    private String token;
}

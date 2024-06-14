package com.example.backend.service;

import com.example.backend.model.dto.LoginDTO;
import com.example.backend.model.vo.UserLoginVO;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 认证服务
 * @create : 2024-06-14 10:23
 **/

public interface AuthService {


    /**
     * 检查用户是否可用
     *
     * @param pkId pkId
     * @return boolean
     */
    boolean checkUserEnabled(Integer pkId);

    /**
     * 用户登录
     *
     * @param loginDTO 登录 DTO
     * @return {@link UserLoginVO }
     */
    UserLoginVO userLogin(LoginDTO loginDTO);

    void logout();
}

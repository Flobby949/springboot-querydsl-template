package com.example.backend.exception;

import lombok.Getter;
import lombok.ToString;

/**
 * @author : Flobby
 * @description : 业务异常枚举
 * @create : 2023-11-20 16:14
 **/

@Getter
@ToString
public enum BusinessExceptionEnum {

    REQUEST_PARAMS_IS_NOT_EMPTY("请求参数不能为空"),
    UNAUTHORIZED("认证未通过"),
    LOGIN_STATUS_EXPIRE("登录状态过期"),
    USER_IS_NOT_EXIST("用户不存在"),
    PASSWORD_IS_WRONG("密码错误"),
    TOKEN_IS_EMPTY("token 不能为空"),
    TOKEN_ERROR("token 过期或者无效"),
    USER_IS_NOT_ENABLED("账号被禁用"),
    OPERATION_FAIL("操作失败"),
    ;

    private final String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }
}

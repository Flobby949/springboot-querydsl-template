package com.example.backend.exception;

import lombok.Getter;

/**
 * @author : Flobby
 * @description : 自定义业务异常
 * @create : 2023-11-20 16:15
 **/

@Getter
public class BusinessException extends RuntimeException{

    private String msg;

    public BusinessException(BusinessExceptionEnum e) {
        this.msg = e.getDesc();
    }

    public BusinessException(String e) {
        this.msg = e;
    }

    /**
     * 不写入堆栈信息，提高性能
     *
     * @return {@link Throwable}
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}

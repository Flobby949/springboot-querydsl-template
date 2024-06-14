package com.example.backend.exception;


import com.example.backend.common.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : Flobby
 * @description : 统一异常处理
 * @create : 2023-11-20 16:10
 **/

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 所有异常的处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(Exception e) {
        log.error("系统异常：", e);
        CommonResp<?> commonResp = new CommonResp<>();
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getMessage());
        return commonResp;
    }


    /**
     * 业务异常的处理
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BusinessException e) {
        log.error("业务异常：{}", e.getMsg());
        CommonResp<?> commonResp = new CommonResp<>();
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getMsg());
        return commonResp;
    }

    /**
     * 参数校验异常的处理
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public CommonResp<?> exceptionHandler(BindException e) {
        log.error("参数校验异常：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        CommonResp<?> commonResp = new CommonResp<>();
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }
}

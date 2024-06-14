package com.example.backend.controller;

import com.example.backend.common.CommonResp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 基础接口
 * @create : 2024-06-14 10:50
 **/

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("common")
public class CommonController {

    @GetMapping("test")
    public CommonResp<String> test() {
        return CommonResp.success("系统启动成功");
    }
}

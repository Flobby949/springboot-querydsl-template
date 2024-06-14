package com.example.backend.controller;

import com.example.backend.common.CommonResp;
import com.example.backend.common.PageResult;
import com.example.backend.model.dto.DemoDTO;
import com.example.backend.model.dto.DemoQueryDTO;
import com.example.backend.model.vo.DemoItemVO;
import com.example.backend.service.DemoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : Flobby
 * @program : backend-template
 * @description :
 * @create : 2024-06-14 13:53
 **/

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("demo")
public class DemoController {

    private final DemoService demoService;

    @PostMapping("saveAndUpdate")
    public CommonResp<?> saveAndUpdate(@RequestBody @Valid DemoDTO demoDTO) {
        demoService.save(demoDTO);
        return CommonResp.success();
    }

    @PostMapping("page")
    public CommonResp<PageResult<DemoItemVO>> queryByPage(@RequestBody @Valid DemoQueryDTO dto) {
        return CommonResp.success(demoService.queryPage(dto));
    }

    @DeleteMapping()
    public CommonResp<?> delete(@RequestParam Integer pkId) {
        demoService.delete(pkId);
        return CommonResp.success();
    }
}

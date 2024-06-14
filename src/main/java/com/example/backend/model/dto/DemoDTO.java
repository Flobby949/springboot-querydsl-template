package com.example.backend.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : Flobby
 * @program : backend-template
 * @create : 2024-06-14 13:33
 **/

@Data
public class DemoDTO {
    private Integer pkId;
    @NotBlank(message = "标题不能为空")
    private String title;
}

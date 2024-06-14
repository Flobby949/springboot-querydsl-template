package com.example.backend.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 分页查询基类
 * @create : 2024-06-14 13:34
 **/

@Data
public class BaseQuery {

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private int page;

    @NotNull(message = "每页条数不能为空")
    @Range(min = 1, max = 100, message = "每页条数，取值范围 1-100")
    private int pageSize;

    /**
     * 计算分页起始位置
     * @return int
     */
    public int getOffset() {
        return (page - 1) * pageSize;
    }
}

package com.example.backend.model.dto;

import com.example.backend.model.BaseQuery;
import lombok.Data;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 分页条件查询
 * @create : 2024-06-14 13:33
 **/

@Data
public class DemoQueryDTO extends BaseQuery {
    private String keyword;
}

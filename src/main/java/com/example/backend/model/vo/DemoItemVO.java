package com.example.backend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author : Flobby
 * @program : backend-template
 * @create : 2024-06-14 13:35
 **/

@Data
public class DemoItemVO {
    private int pkId;
    private int userId;
    private String username;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}

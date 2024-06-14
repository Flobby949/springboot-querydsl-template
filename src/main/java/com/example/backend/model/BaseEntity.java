package com.example.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 实体类基类
 * @create : 2024-06-14 10:33
 **/

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -7554168116148551695L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer pkId;

    @Column(name = "delete_flag")
    private Integer deleteFlag;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}

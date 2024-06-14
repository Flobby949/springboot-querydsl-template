package com.example.backend.model.po;

import com.example.backend.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : demo
 * @create : 2024-06-14 13:29
 **/

@Entity
@Data
@Table(name = "t_demo")
public class DemoPO extends BaseEntity {
    @Column(name = "user_id")
    private Integer userId;
    private String title;
}

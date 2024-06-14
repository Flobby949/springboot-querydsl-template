package com.example.backend.model.po;

import com.example.backend.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 账户实体
 * @create : 2024-06-14 09:50
 **/

@Entity
@Data
@Table(name = "t_account")
public class AccountPO extends BaseEntity {

    private String username;

    private String password;
    @Column(name = "is_enabled")
    private Integer isEnabled;
}

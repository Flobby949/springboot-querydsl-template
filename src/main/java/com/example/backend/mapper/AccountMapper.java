package com.example.backend.mapper;

import com.example.backend.common.Constant;
import com.example.backend.model.po.AccountPO;
import com.example.backend.model.po.QAccountPO;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : mapper
 * @create : 2024-06-14 09:55
 **/

@Repository
public class AccountMapper {
    @Resource
    private JPAQueryFactory jpaQueryFactory;

    private static QAccountPO account = QAccountPO.accountPO;

    /**
     * 按用户名查询用户
     *
     * @param username 用户名
     * @return {@link Object }
     */
    public AccountPO selectUserByUsername(String username) {
        Predicate predicate = account.deleteFlag.eq(Constant.FLAG_TRUE)
                .and(account.username.eq(username));

        return jpaQueryFactory.selectFrom(account)
                .where(predicate).fetchOne();
    }
}

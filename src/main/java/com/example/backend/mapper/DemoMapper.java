package com.example.backend.mapper;

import com.example.backend.common.Constant;
import com.example.backend.model.dto.DemoQueryDTO;
import com.example.backend.model.po.QAccountPO;
import com.example.backend.model.po.QDemoPO;
import com.example.backend.model.vo.DemoItemVO;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author : Flobby
 * @program : backend-template
 * @create : 2024-06-14 13:30
 **/

@Repository
public class DemoMapper {
    @Resource
    private JPAQueryFactory jpaQueryFactory;

    private static QDemoPO demoPO = QDemoPO.demoPO;
    private static QAccountPO accountPO = QAccountPO.accountPO;

    public QueryResults<DemoItemVO> queryByPage(DemoQueryDTO dto) {
        Predicate predicate = demoPO.deleteFlag.eq(Constant.FLAG_TRUE);
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            predicate = ExpressionUtils.and(predicate, demoPO.title.like("%" + dto.getKeyword() + "%"));
        }
        QBean<DemoItemVO> columns = Projections.bean(DemoItemVO.class,
                demoPO.pkId,
                accountPO.pkId.as("userId"),
                accountPO.username,
                demoPO.title,
                demoPO.createTime
        );
        return jpaQueryFactory.select(columns)
                .from(demoPO)
                .leftJoin(accountPO).on(accountPO.pkId.eq(demoPO.userId))
                .where(predicate)
                .offset(dto.getOffset())
                .limit(dto.getPageSize())
                .fetchResults();
    }
}

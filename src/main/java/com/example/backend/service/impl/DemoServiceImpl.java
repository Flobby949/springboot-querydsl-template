package com.example.backend.service.impl;

import com.example.backend.cache.RequestContext;
import com.example.backend.common.Constant;
import com.example.backend.common.PageResult;
import com.example.backend.exception.BusinessException;
import com.example.backend.mapper.DemoMapper;
import com.example.backend.model.dto.DemoDTO;
import com.example.backend.model.dto.DemoQueryDTO;
import com.example.backend.model.po.DemoPO;
import com.example.backend.model.vo.DemoItemVO;
import com.example.backend.repository.DemoRepository;
import com.example.backend.service.DemoService;
import com.example.backend.utils.ConvertBeanUtil;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : Flobby
 * @program : backend-template
 * @description :
 * @create : 2024-06-14 13:38
 **/

@Service
@AllArgsConstructor
public class DemoServiceImpl implements DemoService {

    private final DemoRepository demoRepository;
    private final DemoMapper demoMapper;

    @Override
    public void save(DemoDTO dto) {
        DemoPO demo = ConvertBeanUtil.convert(dto, DemoPO.class);
        if (ObjectUtils.isEmpty(dto.getPkId())) {
            // 新增
            demo.setCreateTime(new Date());
        } else {
            // 修改
            DemoPO originDemo = demoRepository.findByPkId(dto.getPkId()).orElseThrow(() -> new BusinessException("记录为空"));
            if (!originDemo.getUserId().equals(RequestContext.getUserId())) {
                throw new BusinessException("请勿修改他人记录");
            }
            demo.setCreateTime(originDemo.getCreateTime());
        }
        demo.setUserId(RequestContext.getUserId());
        demo.setDeleteFlag(Constant.FLAG_TRUE);
        demo.setUpdateTime(new Date());
        demoRepository.saveAndFlush(demo);
    }

    @Override
    public PageResult<DemoItemVO> queryPage(DemoQueryDTO dto) {
        QueryResults<DemoItemVO> result = demoMapper.queryByPage(dto);
        return new PageResult<>(result.getResults(), result.getTotal());
    }

    @Override
    public void delete(int pkId) {
        DemoPO originDemo = demoRepository.findByPkId(pkId).orElseThrow(() -> new BusinessException("记录为空"));
        if (!originDemo.getUserId().equals(RequestContext.getUserId())) {
            throw new BusinessException("请勿删除他人记录");
        }
        demoRepository.logicDelete(pkId);
    }
}

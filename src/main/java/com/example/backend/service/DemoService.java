package com.example.backend.service;

import com.example.backend.common.PageResult;
import com.example.backend.model.dto.DemoDTO;
import com.example.backend.model.dto.DemoQueryDTO;
import com.example.backend.model.vo.DemoItemVO;

/**
 * @author : Flobby
 * @program : backend-template
 * @description :
 * @create : 2024-06-14 13:32
 **/

public interface DemoService {

    void save(DemoDTO dto);

    PageResult<DemoItemVO> queryPage(DemoQueryDTO dto);

    void delete(int pkId);
}

package com.example.backend.service.impl;

import com.example.backend.cache.RedisCache;
import com.example.backend.cache.RedisKeys;
import com.example.backend.cache.RequestContext;
import com.example.backend.cache.TokenStoreCache;
import com.example.backend.common.Constant;
import com.example.backend.exception.BusinessException;
import com.example.backend.exception.BusinessExceptionEnum;
import com.example.backend.mapper.AccountMapper;
import com.example.backend.model.dto.LoginDTO;
import com.example.backend.model.po.AccountPO;
import com.example.backend.model.vo.UserLoginVO;
import com.example.backend.repository.AccountRepository;
import com.example.backend.service.AuthService;
import com.example.backend.utils.MD5Util;
import com.example.backend.utils.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : 认证服务实现类
 * @create : 2024-06-14 10:23
 **/

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final RedisCache redisCache;
    private final TokenStoreCache tokenStoreCache;

    @Override
    public boolean checkUserEnabled(Integer pkId) {
        AccountPO account = accountRepository.findByPkId(pkId)
                .orElseThrow(() -> new BusinessException(BusinessExceptionEnum.USER_IS_NOT_EXIST));
        return account.getIsEnabled().equals(Constant.FLAG_TRUE);
    }

    @Override
    public UserLoginVO userLogin(LoginDTO loginDTO) {
        String encodePassword = MD5Util.MD5Encode(loginDTO.getPassword());
        AccountPO accountPO = accountMapper.selectUserByUsername(loginDTO.getUsername());
        if (ObjectUtils.isEmpty(accountPO)) {
            // 一键注册
            AccountPO newAccount = new AccountPO();
            newAccount.setUsername(loginDTO.getUsername());
            newAccount.setPassword(encodePassword);
            newAccount.setIsEnabled(Constant.FLAG_TRUE);
            newAccount.setDeleteFlag(Constant.FLAG_TRUE);
            newAccount.setCreateTime(new Date());
            newAccount.setUpdateTime(new Date());
            Integer newUserId = accountRepository.saveAndFlush(newAccount).getPkId();
            return this.generateUserLoginVO(newUserId);
        } else {
            if (!accountPO.getPassword().equals(encodePassword)) {
                throw new BusinessException(BusinessExceptionEnum.PASSWORD_IS_WRONG);
            }
            if (accountPO.getIsEnabled().equals(Constant.FLAG_FALSE)) {
                throw new BusinessException(BusinessExceptionEnum.USER_IS_NOT_ENABLED);
            }
            return this.generateUserLoginVO(accountPO.getPkId());
        }
    }

    private UserLoginVO generateUserLoginVO(Integer pkId) {
        String accessToken = TokenUtil.createToken(pkId);
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setToken(accessToken);
        userLoginVO.setPkId(pkId);
        tokenStoreCache.saveUser(accessToken, userLoginVO);
        return userLoginVO;
    }

    @Override
    public void logout() {
// 从上下文中获取userId，然后获取redisKey
        String cacheKey = RedisKeys.getUserIdKey(RequestContext.getUserId());
        // 通过userId，获取redis中的 accessToken
        Object cacheObj = redisCache.get(cacheKey);
        if (cacheObj instanceof String) {
            String accessToken = (String) cacheObj;
            System.out.println("[AuthServiceImpl]accessToken = " + accessToken);
            // 删除缓存中的 token
            redisCache.delete(cacheKey);
            // 删除缓存中的用户信息
            log.info("删除缓存中的用户信息, accessToken: {}", accessToken);
            tokenStoreCache.deleteUser(accessToken);
            return;
        }
        throw new BusinessException(BusinessExceptionEnum.OPERATION_FAIL);
    }
}

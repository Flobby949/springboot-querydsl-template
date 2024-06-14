package com.example.backend.interceptor;


import com.example.backend.cache.RequestContext;
import com.example.backend.cache.TokenStoreCache;
import com.example.backend.common.Constant;
import com.example.backend.exception.BusinessException;
import com.example.backend.exception.BusinessExceptionEnum;
import com.example.backend.model.vo.UserLoginVO;
import com.example.backend.service.AuthService;
import com.example.backend.utils.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Flobby
 */
@Slf4j
@AllArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final TokenStoreCache tokenStoreCache;
    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取token
        String accessToken = TokenUtil.getAccessToken(request);
        if (StringUtils.isBlank(accessToken)) {
            throw new BusinessException(BusinessExceptionEnum.UNAUTHORIZED);
        }
        // 验证用户是否可用
        UserLoginVO user = tokenStoreCache.getUser(accessToken);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(BusinessExceptionEnum.LOGIN_STATUS_EXPIRE);
        }
        boolean enabled = authService.checkUserEnabled(user.getPkId());
        if (!enabled) {
            throw new BusinessException(BusinessExceptionEnum.USER_IS_NOT_ENABLED);
        }
        RequestContext.put(Constant.USER_ID, user.getPkId());
        return true;
    }

}
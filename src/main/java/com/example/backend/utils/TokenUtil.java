package com.example.backend.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Flobby
 * @program : backend-template
 * @description : jwt工具
 * @create : 2024-06-14 09:55
 **/

public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * 盐值很重要，不能泄漏，且每个项目都应该不一样，可以放到配置文件中
     */
    private static final String SALT_KEY = "backend-template";
    /**
     * 过期时间，默认一天
     */
    private static final Integer EXPIRE_TIME = 60 * 60 * 24;

    /**
     * 创建令牌
     *
     * @param id     id
     * @return {@link String}
     */
    public static String createToken(Integer id) {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.SECOND, EXPIRE_TIME);
        Map<String, Object> payload = new HashMap<>();
        // 签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        // 内容
        payload.put("id", id);
        String token = JWTUtil.createToken(payload, SALT_KEY.getBytes());
        logger.info("生成JWT token：{}", token);
        return token;
    }

    public static boolean validate(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(SALT_KEY.getBytes());
        // validate包含了verify
        boolean validate = jwt.validate(0);
        logger.info("JWT token校验结果：{}", validate);
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(SALT_KEY.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        logger.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

    /**
     * 获取 AccessToken
     */
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter("accessToken");
        }
        logger.info("accessToken: {}", accessToken);
        return accessToken;
    }
}

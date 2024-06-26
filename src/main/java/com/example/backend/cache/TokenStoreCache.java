package com.example.backend.cache;

import com.alibaba.fastjson2.JSON;
import com.example.backend.model.vo.UserLoginVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author flobby
 */
@Component
@AllArgsConstructor
public class TokenStoreCache {
    private final RedisCache redisCache;

    public void saveUser(String accessToken, UserLoginVO user) {
        String accessTokenKey = RedisKeys.getAccessTokenKey(accessToken);
        String managerIdKey = RedisKeys.getUserIdKey(user.getPkId());
        if (redisCache.get(managerIdKey) != null) {
            redisCache.delete(String.valueOf(redisCache.get(managerIdKey)));
        }
        redisCache.set(managerIdKey, accessToken);
        redisCache.set(accessTokenKey, user);
    }

    public UserLoginVO getUser(String accessToken) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        return JSON.to(UserLoginVO.class, redisCache.get(key));
    }

    public void deleteUser(String accessToken) {
        String key = RedisKeys.getAccessTokenKey(accessToken);
        redisCache.delete(key);
    }

    public void deleteUserById(Integer id) {
        String managerIdKey = RedisKeys.getUserIdKey(id);
        String key = String.valueOf(redisCache.get(managerIdKey));
        redisCache.delete(key);
    }

    public void deleteUserByIds(List<Integer> ids) {
        List<String> keys = new ArrayList<>();
        for (Integer id : ids) {
            String managerIdKey = RedisKeys.getUserIdKey(id);
            String key = String.valueOf(redisCache.get(managerIdKey));
            keys.add(key);
        }
        redisCache.delete(keys);
    }
}

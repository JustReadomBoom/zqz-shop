package com.zqz.shop.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.zqz.shop.bean.UserToken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 维护用户token
 */
public class UserTokenManager {
    private static Map<String, UserToken> tokenMap = new HashMap<>();
    private static Map<Integer, UserToken> idMap = new HashMap<>();
    private static Date now = new Date();

    public static Integer getUserId(String token) {
        UserToken userToken = tokenMap.get(token);
        if (userToken == null) {
            return null;
        }

        if (userToken.getExpireTime().before(now)) {
            tokenMap.remove(token);
            idMap.remove(userToken.getUserId());
            return null;
        }

        return userToken.getUserId();
    }

    public static UserToken generateToken(Integer id) {
        try {
            UserToken userToken;
            String token = RandomUtil.randomString(32);
            while (tokenMap.containsKey(token)) {
                token = RandomUtil.randomString(32);
            }

            Date tomorrow = DateUtil.offsetDay(now, +1).toJdkDate();
            userToken = new UserToken();
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(tomorrow);
            userToken.setUserId(id);
            tokenMap.put(token, userToken);
            idMap.put(id, userToken);
            return userToken;
        } catch (Exception e) {
            throw new RuntimeException(String.format("微信获取token失败:%s", e.getMessage()));
        }

    }

    public static String getSessionKey(Integer userId) {
        UserToken userToken = idMap.get(userId);
        if (userToken == null) {
            return null;
        }
        if (userToken.getExpireTime().before(now)) {
            tokenMap.remove(userToken.getToken());
            idMap.remove(userId);
            return null;
        }

        return userToken.getSessionKey();
    }

    public static void removeToken(Integer userId) {
        UserToken userToken = idMap.get(userId);
        String token = userToken.getToken();
        idMap.remove(userId);
        tokenMap.remove(token);
    }
}

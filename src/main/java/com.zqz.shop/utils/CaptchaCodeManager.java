package com.zqz.shop.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;


/**
 * @Author: ZQZ
 * @Description: 缓存系统中的验证码
 * @ClassName: CaptchaCodeManager
 * @Date: Created in 11:50 2023-8-11
 */
@Slf4j
public class CaptchaCodeManager {

    /**
     * 验证码默认存储的有效期，单位：分钟
     */
    private static final Integer DEFAULT_EXPIRE_MINUTES = 3;
    private static Map<String, CaptchaItem> captchaCodeCache = new HashMap<>();

    /**
     * 添加到缓存
     *
     * @param flagUid
     * @param code
     * @param expireTime
     * @return
     */
    public static boolean addToCache(String flagUid, String code, Integer expireTime) {
        //清理过期内存数据
        cleanExpireCacheData();

        // 已经发过验证码且验证码还未过期
        if (captchaCodeCache.get(flagUid) != null) {
            if (captchaCodeCache.get(flagUid).getExpireTime().isAfter(LocalDateTime.now())) {
                return false;
            } else {
                // 存在但是已过期，删掉
                captchaCodeCache.remove(flagUid);
            }
        }
        CaptchaItem captchaItem = new CaptchaItem();
        captchaItem.setFlagUid(flagUid);
        captchaItem.setCode(code);
        // 有效期为expireTime分钟
        if (expireTime == null) {
            expireTime = DEFAULT_EXPIRE_MINUTES;
        }
        captchaItem.setExpireTime(LocalDateTime.now().plusMinutes(expireTime));
        captchaCodeCache.put(flagUid, captchaItem);
        return true;
    }


    /**
     * 获取缓存的验证码
     *
     * @param flagUid
     * @return
     */
    public static String getCachedCaptcha(String flagUid) {
        // 没有标志码记录
        if (captchaCodeCache.get(flagUid) == null) {
            return null;
        }

        // 记录但是已经过期
        if (captchaCodeCache.get(flagUid).getExpireTime().isBefore(LocalDateTime.now())) {
            return null;
        }
        //清理过期内存数据
        cleanExpireCacheData();

        return captchaCodeCache.get(flagUid).getCode();
    }

    /**
     * 清理过期验证码
     */
    private static void cleanExpireCacheData() {
        //map.entrySet()得到的是set集合，可以使用迭代器遍历
        Iterator<Entry<String, CaptchaItem>> iterator = captchaCodeCache.entrySet().iterator();
        List<String> keys = new ArrayList<>();
        while (iterator.hasNext()) {
            Entry<String, CaptchaItem> entry = iterator.next();
            if (entry.getValue() != null && entry.getValue().getExpireTime().isBefore(LocalDateTime.now())) {
                keys.add(entry.getKey());
                log.info("清理商品分享图 验证标志码flagUid:{},验证码 captcha:{}", entry.getKey(), entry.getValue().getCode());
            }
        }
        if (keys.size() > 0) {
            for (String key : keys) {
                captchaCodeCache.remove(key);
            }
        }
    }

}

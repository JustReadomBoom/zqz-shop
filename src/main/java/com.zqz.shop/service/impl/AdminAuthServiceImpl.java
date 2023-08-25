package com.zqz.shop.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zqz.shop.bean.UserToken;
import com.zqz.shop.bean.admin.AdminLoginReq;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.enums.AdminResponseCode;
import com.zqz.shop.exception.ShopException;
import com.zqz.shop.service.AdminAuthService;
import com.zqz.shop.service.business.AdminBusService;
import com.zqz.shop.utils.CaptchaCodeManager;
import com.zqz.shop.utils.ResponseUtil;
import com.zqz.shop.utils.UserTokenManager;
import com.zqz.shop.utils.VerifyCodeUtil;
import com.zqz.shop.utils.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAuthServiceImpl
 * @Date: Created in 10:08 2023-8-25
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {
    @Autowired
    private AdminBusService adminBusService;


    @Override
    public Object doLogin(AdminLoginReq req) {
        String userName = req.getUserName();
        String passWord = req.getPassWord();
        String code = req.getCode();
        String uuid = req.getUuid();

        String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(uuid);
        if (StrUtil.isBlank(cachedCaptcha)) {
            log.error("登陆失败，{}", AdminResponseCode.AUTH_CAPTCHA_EXPIRED.getDesc());
            return ResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_EXPIRED);
        }
        if (!code.equalsIgnoreCase(cachedCaptcha)) {
            log.error("登陆失败，{}，输入验证码:{}，缓存验证码:{}", AdminResponseCode.AUTH_CAPTCHA_ERROR.getDesc(), code, cachedCaptcha);
            return ResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_ERROR);
        }
        List<Admin> adminList = adminBusService.queryByUserName(userName);
        if (CollectionUtil.isEmpty(adminList)) {
            throw new ShopException(AdminResponseCode.AUTH_ADMIN_NOT_EXIST.getDesc());
        }
        Admin admin = adminList.get(0);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(passWord, admin.getPassword())){
            log.error("登陆失败，密码不正确");
            throw new ShopException(AdminResponseCode.AUTH_PASSWORD_ERROR.getDesc());
        }
        UserToken userToken = UserTokenManager.generateToken(admin.getId());
        return ResponseUtil.ok(userToken.getToken());
    }

    @Override
    public Object doGetImgCode() {
        // 生成随机字串
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        // 唯一标识
        String uuid = RandomUtil.randomString(32);
        boolean successful = CaptchaCodeManager.addToCache(uuid, verifyCode, 10);
        if (!successful) {
            log.error("获取验证码失败，{}", AdminResponseCode.AUTH_CAPTCHA_FREQUENCY.getDesc());
            return ResponseUtil.fail(AdminResponseCode.AUTH_CAPTCHA_FREQUENCY);
        }
        // 生成图片
        ByteArrayOutputStream stream = null;
        try {
            int w = 111, h = 36;
            stream = new ByteArrayOutputStream();
            VerifyCodeUtil.outputImage(w, h, stream, verifyCode);
            Map<String, Object> data = new HashMap<>(2);
            data.put("uuid", uuid);
            data.put("img", Base64.encode(stream.toByteArray()));
            return ResponseUtil.ok(data);
        } catch (Exception e) {
            log.error("获取验证码失败，{}", e.getMessage());
            e.printStackTrace();
            return ResponseUtil.serious();
        } finally {
            try {
                if (null != stream) {
                    stream.close();
                }
            } catch (Exception ce) {
                ce.printStackTrace();
            }
        }
    }
}

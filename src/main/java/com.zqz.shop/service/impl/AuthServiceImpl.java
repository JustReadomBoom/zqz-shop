package com.zqz.shop.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zqz.shop.bean.req.BindPhoneReq;
import com.zqz.shop.bean.UserInfo;
import com.zqz.shop.bean.UserToken;
import com.zqz.shop.bean.WxLoginInfo;
import com.zqz.shop.bean.resp.BindPhoneResp;
import com.zqz.shop.bean.resp.WxLoginResp;
import com.zqz.shop.entity.User;
import com.zqz.shop.enums.UserTypeEnum;
import com.zqz.shop.exception.ShopException;
import com.zqz.shop.service.AuthService;
import com.zqz.shop.service.business.UserBusService;
import com.zqz.shop.utils.IpUtil;
import com.zqz.shop.utils.ResponseUtil;
import com.zqz.shop.utils.UserTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AuthServiceImpl
 * @Date: Created in 10:10 2023-8-11
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserBusService userBusService;
    @Autowired
    private WxMaService wxMaService;


    @Override
    public Object doWxLogin(WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        try {
            String code = wxLoginInfo.getCode();
            UserInfo userInfo = wxLoginInfo.getUserInfo();

            WxLoginResp loginResp = new WxLoginResp();

            Integer shareUserId = wxLoginInfo.getShareUserId();
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(code);
            String sessionKey = result.getSessionKey();
            String openId = result.getOpenid();

            if (StrUtil.isBlank(sessionKey) || StrUtil.isBlank(openId)) {
                throw new ShopException("微信登录获取sessionKey和openId失败!");
            }
            String ip = IpUtil.getIpAddr(request);
            User user = userBusService.queryUserByOpenId(openId);
            Date now = new Date();

            if (ObjectUtil.isEmpty(user)) {
                //新用户
                user = new User();
                user.setUsername(openId);
                user.setPassword(openId);
                user.setWeixinOpenid(openId);
                user.setAvatar(userInfo.getAvatarUrl());
                user.setNickname(userInfo.getNickName());
                user.setGender(userInfo.getGender());
                user.setUserLevel((byte) 0);
                user.setStatus((byte) 0);
                user.setLastLoginTime(now);
                user.setLastLoginIp(ip);
                user.setShareUserId(shareUserId);
                user.setAddTime(now);
                user.setUpdateTime(now);
                userBusService.add(user);
            } else {
                user.setLastLoginTime(now);
                user.setLastLoginIp(ip);
                int up = userBusService.updateUserById(user);
                if (up <= 0) {
                    return ResponseUtil.updatedDataFailed();
                }
            }

            UserToken userToken = UserTokenManager.generateToken(user.getId());
            userToken.setSessionKey(sessionKey);

            loginResp.setToken(userToken.getToken());
            loginResp.setTokenExpire(userToken.getExpireTime().toString());

            userInfo.setUserId(user.getId());
            if (StrUtil.isNotBlank(user.getMobile())) {
                userInfo.setPhone(user.getMobile());
            }

            String registerTime = DateUtil.format(ObjectUtil.isEmpty(user.getAddTime()) ? new Date() : user.getAddTime(), DatePattern.NORM_DATE_PATTERN);
            userInfo.setRegisterDate(registerTime);
            userInfo.setStatus(user.getStatus());
            userInfo.setUserLevel(user.getUserLevel());
            userInfo.setUserLevelDesc(UserTypeEnum.getInstance(user.getUserLevel()).getDesc());
            loginResp.setUserInfo(userInfo);
            return ResponseUtil.ok(loginResp);
        } catch (Exception e) {
            throw new ShopException(String.format("微信登录失败:%s", e.getMessage()));
        }
    }

    @Override
    public Object doBindPhone(Integer userId, BindPhoneReq req) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }

        BindPhoneResp bindPhoneResp = new BindPhoneResp();

        String encryptedData = req.getEncryptedData();
        String iv = req.getIv();
        String sessionKey = UserTokenManager.getSessionKey(userId);
        WxMaPhoneNumberInfo phoneNoInfo;
        try {
            phoneNoInfo = this.wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        } catch (Exception e) {
            throw new ShopException(String.format("绑定手机号失败:%s", e.getMessage()));
        }
        String phoneNumber = phoneNoInfo.getPhoneNumber();
        User user = userBusService.queryById(userId);
        if (ObjectUtil.isEmpty(user)) {
            log.error("绑定手机号失败，用户:{}信息为空!", userId);
            return ResponseUtil.dataEmpty();
        }
        user.setMobile(phoneNumber);
        int update = userBusService.update(user);
        if (update <= 0) {
            return ResponseUtil.fail();
        }
        bindPhoneResp.setPhone(phoneNumber);
        return ResponseUtil.ok(bindPhoneResp);
    }


}

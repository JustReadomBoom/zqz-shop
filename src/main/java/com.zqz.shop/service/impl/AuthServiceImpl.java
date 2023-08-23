package com.zqz.shop.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.bean.BindPhoneReq;
import com.zqz.shop.bean.UserInfo;
import com.zqz.shop.bean.UserToken;
import com.zqz.shop.bean.WxLoginInfo;
import com.zqz.shop.entity.User;
import com.zqz.shop.enums.ResponseCode;
import com.zqz.shop.enums.UserTypeEnum;
import com.zqz.shop.mapper.UserMapper;
import com.zqz.shop.service.AuthService;
import com.zqz.shop.utils.IpUtil;
import com.zqz.shop.utils.ResponseUtil;
import com.zqz.shop.utils.UserTokenManager;
import com.zqz.shop.utils.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zqz.shop.entity.table.UserTableDef.USER;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AuthServiceImpl
 * @Date: Created in 10:10 2023-8-11
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private WxMaService wxMaService;

    @Override
    public Object doLogin(String body, HttpServletRequest request) {
        log.info("======登录入参:{}", body);
        try {
            Map<Object, Object> result = new HashMap<>(3);
            String userName = JSONUtil.parseObj(body).getStr("username");
            String passWord = JSONUtil.parseObj(body).getStr("password");
            List<User> userList = queryUserByName(userName);
            User user;
            if (userList.size() > 1) {
                return ResponseUtil.serious();
            } else if (userList.size() == 0) {
                return ResponseUtil.badArgumentValue();
            } else {
                user = userList.get(0);
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(passWord, user.getPassword())) {
                return ResponseUtil.fail(ResponseCode.AUTH_INVALID_ACCOUNT);
            }

            UserInfo userInfo = new UserInfo();
            userInfo.setNickName(userName);
            userInfo.setAvatarUrl(user.getAvatar());

            String registerTime = DateUtil.format(ObjectUtil.isEmpty(user.getAddTime()) ? new Date() : user.getAddTime(), DatePattern.NORM_DATE_PATTERN);
            userInfo.setRegisterDate(registerTime);
            userInfo.setStatus(user.getStatus());
            userInfo.setUserLevel(user.getUserLevel());
            userInfo.setUserLevelDesc(UserTypeEnum.getInstance(user.getUserLevel()).getDesc());

            UserToken userToken = UserTokenManager.generateToken(user.getId());

            result.put("token", userToken.getToken());
            result.put("tokenExpire", userToken.getExpireTime().toString());
            result.put("userInfo", userInfo);

            return ResponseUtil.ok(result);
        } catch (Exception e) {
            log.error("****** doLogin error:{}", e.getMessage());
            return ResponseUtil.fail(e.getMessage());
        }


    }

    @Override
    public Object doWxLogin(WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        log.info("======微信登录入参:{}", JSONUtil.toJsonStr(wxLoginInfo));
        try {
            String code = wxLoginInfo.getCode();
            UserInfo userInfo = wxLoginInfo.getUserInfo();
            if (StrUtil.isBlank(code) || ObjectUtil.isEmpty(userInfo)) {
                return ResponseUtil.badArgument();
            }

            Integer shareUserId = wxLoginInfo.getShareUserId();
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(code);
            String sessionKey = result.getSessionKey();
            String openId = result.getOpenid();

            if (StrUtil.isBlank(sessionKey) || StrUtil.isBlank(openId)) {
                log.error("******微信登录获取sessionKey和openId失败");
                return ResponseUtil.fail();
            }
            String ip = IpUtil.getIpAddr(request);
            User user = queryUserByOpenId(openId);
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
                userMapper.insertSelective(user);

            } else {
                user.setLastLoginTime(now);
                user.setLastLoginIp(ip);
                int up = updateUserById(user);
                if (up <= 0) {
                    return ResponseUtil.updatedDataFailed();
                }
            }

            UserToken userToken = UserTokenManager.generateToken(user.getId());
            userToken.setSessionKey(sessionKey);
            Map<Object, Object> resultMap = new HashMap<>(3);
            resultMap.put("token", userToken.getToken());
            resultMap.put("tokenExpire", userToken.getExpireTime().toString());
            userInfo.setUserId(user.getId());
            if (StrUtil.isNotBlank(user.getMobile())) {
                userInfo.setPhone(user.getMobile());
            }

            String registerTime = DateUtil.format(ObjectUtil.isEmpty(user.getAddTime()) ? new Date() : user.getAddTime(), DatePattern.NORM_DATE_PATTERN);
            userInfo.setRegisterDate(registerTime);
            userInfo.setStatus(user.getStatus());
            userInfo.setUserLevel(user.getUserLevel());
            userInfo.setUserLevelDesc(UserTypeEnum.getInstance(user.getUserLevel()).getDesc());
            resultMap.put("userInfo", userInfo);
            return ResponseUtil.ok(resultMap);
        } catch (Exception e) {
            log.error("****** doWxLogin error:{}", e.getMessage());
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @Override
    public Object doBindPhone(Integer userId, BindPhoneReq req) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        String encryptedData = req.getEncryptedData();
        String iv = req.getIv();
        String sessionKey = UserTokenManager.getSessionKey(userId);
        WxMaPhoneNumberInfo phoneNoInfo;
        try {
            phoneNoInfo = this.wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        } catch (Exception e) {
            log.error("绑定手机号失败:{}", e.getMessage());
            e.printStackTrace();
            return ResponseUtil.fail();
        }
        String phoneNumber = phoneNoInfo.getPhoneNumber();
        User user = userMapper.selectOneById(userId);
        if (ObjectUtil.isEmpty(user)) {
            log.error("绑定手机号失败，用户:{}信息为空!", userId);
            return ResponseUtil.dataEmpty();
        }
        user.setMobile(phoneNumber);
        int update = userMapper.update(user);
        if (update <= 0) {
            return ResponseUtil.fail();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("phone", phoneNumber);
        return ResponseUtil.ok(result);
    }

    private List<User> queryUserByName(String userName) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(USER.USERNAME.eq(userName))
                .and(USER.DELETED.eq(false));
        return userMapper.selectListByQuery(wrapper);

    }

    private User queryUserByOpenId(String openId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(USER.WEIXIN_OPENID.eq(openId))
                .and(USER.DELETED.eq(false));
        return userMapper.selectOneByQuery(wrapper);

    }


    private int updateUserById(User user) {
        return userMapper.update(user);
    }


}

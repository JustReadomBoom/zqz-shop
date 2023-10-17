package com.zqz.shop.service.business;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.bean.admin.DayStatis;
import com.zqz.shop.bean.admin.UserVo;
import com.zqz.shop.common.Constant;
import com.zqz.shop.entity.User;
import com.zqz.shop.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static com.zqz.shop.entity.table.UserTableDef.USER;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserBusService
 * @Date: Created in 16:34 2023-8-15
 */
@Service
public class UserBusService {
    @Resource
    private UserMapper userMapper;

    public User queryById(Integer userId) {
        return userMapper.selectOneById(userId);
    }


    public int add(User user) {
        return userMapper.insertSelective(user);
    }


    public int update(User user) {
        return userMapper.update(user);
    }

    public int updateUserById(User user) {
        return userMapper.update(user);
    }

    public User queryUserByOpenId(String openId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(USER.WEIXIN_OPENID.eq(openId))
                .and(USER.DELETED.eq(false));
        return userMapper.selectOneByQuery(wrapper);

    }

    public List<DayStatis> queryRecentCount(Integer day) {
        return userMapper.queryRecentCount(day);
    }

    public Integer queryCount() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(USER.DELETED.eq(false));
        return (int) userMapper.selectCountByQuery(wrapper);

    }

    public List<Map> statUser() {
        return userMapper.statUser();
    }

    public Page<User> queryPage(String username, String mobile, Integer page, Integer limit) {
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select().where(Constant.WHERE_ONE_TO_ONE);
        if (StrUtil.isNotBlank(username)) {
            select = select.and(USER.NICKNAME.like(username));
        }
        if (StrUtil.isNotBlank(mobile)) {
            select = select.and(USER.MOBILE.eq(mobile));
        }
        select.and(USER.DELETED.eq(false))
                .orderBy(USER.ADD_TIME.desc());
        return userMapper.paginateWithRelations(page, limit, select);
    }


    public UserVo queryUserVoById(Integer userId) {
        User user = queryById(userId);
        UserVo userVo = new UserVo();
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        return userVo;
    }
}

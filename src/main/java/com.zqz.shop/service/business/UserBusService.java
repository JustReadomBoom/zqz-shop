package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.User;
import com.zqz.shop.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}

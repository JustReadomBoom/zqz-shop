package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.UserAddress;
import com.zqz.shop.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.zqz.shop.entity.table.UserAddressTableDef.USER_ADDRESS;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserAddressBusService
 * @Date: Created in 14:42 2023-8-22
 */
@Service
public class UserAddressBusService {
    @Resource
    private UserAddressMapper addressMapper;


    public UserAddress queryDefault(Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(USER_ADDRESS.USER_ID.eq(userId))
                .and(USER_ADDRESS.IS_DEFAULT.eq(true))
                .and(USER_ADDRESS.DELETED.eq(false));
        return addressMapper.selectOneByQuery(wrapper);
    }

    public UserAddress queryById(Integer addressId) {
        return addressMapper.selectOneById(addressId);
    }

    public List<UserAddress> queryByUserId(Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(USER_ADDRESS.USER_ID.eq(userId))
                .and(USER_ADDRESS.DELETED.eq(false));
        return addressMapper.selectListByQuery(wrapper);
    }

    public int resetDefault(Integer userId){
        return addressMapper.resetDefault(userId);
    }

    public int add(UserAddress address) {
        address.setAddTime(new Date());
        return addressMapper.insertSelective(address);
    }

    public int update(UserAddress address) {
        address.setUpdateTime(new Date());
        return addressMapper.update(address);
    }
}

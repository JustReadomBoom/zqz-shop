package com.zqz.shop.service.business;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.bean.AddressDetailVo;
import com.zqz.shop.bean.AddressToVo;
import com.zqz.shop.entity.UserAddress;
import com.zqz.shop.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    private RegionBusService regionBusService;


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

    public int resetDefault(Integer userId) {
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

    public Page<UserAddress> queryPage(Integer page, Integer limit, Integer userId, String name) {
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select().where("Constant.WHERE_ONE_TO_ONE");

        if (ObjectUtil.isNotEmpty(userId)) {
            select = select.and(USER_ADDRESS.USER_ID.eq(userId));
        }

        if (StrUtil.isNotBlank(name)) {
            select = select.and(USER_ADDRESS.NAME.like(name));
        }
        select.and(USER_ADDRESS.DELETED.eq(false))
                .orderBy(USER_ADDRESS.ADD_TIME.desc());
        return addressMapper.paginateWithRelations(page, limit, select);
    }

    public AddressToVo toVo(UserAddress address) {
        AddressToVo addressVo = new AddressToVo();
        addressVo.setId(address.getId());
        addressVo.setUserId(address.getUserId());
        addressVo.setName(address.getName());
        addressVo.setMobile(address.getMobile());
        addressVo.setIsDefault(address.getIsDefault());
        addressVo.setProvinceId(address.getProvinceId());
        addressVo.setCityId(address.getCityId());
        addressVo.setAreaId(address.getAreaId());
        addressVo.setAddress(address.getAddress());
        String province = regionBusService.queryById(address.getProvinceId()).getName();
        String city = regionBusService.queryById(address.getCityId()).getName();
        String area = regionBusService.queryById(address.getAreaId()).getName();
        addressVo.setProvince(province);
        addressVo.setCity(city);
        addressVo.setArea(area);
        return addressVo;
    }
}

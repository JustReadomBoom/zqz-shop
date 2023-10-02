package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.zqz.shop.bean.AddressDetailVo;
import com.zqz.shop.bean.AddressVo;
import com.zqz.shop.entity.Region;
import com.zqz.shop.entity.UserAddress;
import com.zqz.shop.service.UserAddressService;
import com.zqz.shop.service.business.RegionBusService;
import com.zqz.shop.service.business.UserAddressBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserAddressServiceImpl
 * @Date: Created in 14:42 2023-8-22
 */
@Service
@Slf4j
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressBusService addressBusService;
    @Autowired
    private RegionBusService regionBusService;

    @Override
    public Object doQueryList(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<UserAddress> addressList = addressBusService.queryByUserId(userId);
        if (CollectionUtil.isEmpty(addressList)) {
            return ResponseUtil.ok();
        }
        List<AddressVo> addressVoList = new ArrayList<>(addressList.size());
        List<Region> regionList = regionBusService.getRegions();
        if (CollectionUtil.isEmpty(regionList)) {
            return ResponseUtil.ok();
        }
        for (UserAddress address : addressList) {
            AddressVo addressVo = new AddressVo();
            String province = regionList.stream().filter(region -> region.getId().equals(address.getProvinceId())).findAny().map(Region::getName).orElse(null);
            String city = regionList.stream().filter(region -> region.getId().equals(address.getCityId())).findAny().map(Region::getName).orElse(null);
            String area = regionList.stream().filter(region -> region.getId().equals(address.getAreaId())).findAny().map(Region::getName).orElse(null);
            String addr = address.getAddress();
            String detailedAddress = province + city + area + " " + addr;

            addressVo.setId(address.getId());
            addressVo.setName(address.getName());
            addressVo.setMobile(address.getMobile());
            addressVo.setIsDefault(address.getIsDefault());
            addressVo.setDetailedAddress(detailedAddress);

            addressVoList.add(addressVo);
        }
        return ResponseUtil.ok(addressVoList);
    }

    @Override
    public Object doAddAddress(Integer userId, UserAddress address) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Object validate = validate(address);
        if (ObjectUtil.isNotEmpty(validate)) {
            return validate;
        }
        //如果设置本次地址为默认地址，则需要重置其他收货地址的默认选项
        if (address.getIsDefault()) {
            addressBusService.resetDefault(userId);
        }
        if (ObjectUtil.isEmpty(address.getId()) || address.getId().equals(0)) {
            address.setUserId(userId);
            addressBusService.add(address);
        } else {
            // 更新地址
            address.setUserId(userId);
            if (addressBusService.update(address) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }
        return ResponseUtil.ok(address.getId());
    }

    @Override
    public Object doDetail(Integer userId, Integer id) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        UserAddress address = addressBusService.queryById(id);
        if (ObjectUtil.isEmpty(address)) {
            log.error("查看地址详情失败，地址信息不存在!");
            return ResponseUtil.dataEmpty();
        }
        AddressDetailVo detailVo = new AddressDetailVo();
        String provinceName = regionBusService.queryById(address.getProvinceId()).getName();
        String cityName = regionBusService.queryById(address.getCityId()).getName();
        String areaName = regionBusService.queryById(address.getAreaId()).getName();

        detailVo.setId(address.getId());
        detailVo.setName(address.getName());
        detailVo.setProvinceId(address.getProvinceId());
        detailVo.setCityId(address.getCityId());
        detailVo.setAreaId(address.getAreaId());
        detailVo.setMobile(address.getMobile());
        detailVo.setAddress(address.getAddress());
        detailVo.setIsDefault(address.getIsDefault());
        detailVo.setProvinceName(provinceName);
        detailVo.setCityName(cityName);
        detailVo.setAreaName(areaName);

        return ResponseUtil.ok(detailVo);
    }


    private Object validate(UserAddress address) {
        String name = address.getName();
        if (StrUtil.isBlank(name)) {
            return ResponseUtil.badArgument();
        }

        // 测试收货手机号码是否正确
        String mobile = address.getMobile();
        if (StrUtil.isBlank(mobile)) {
            return ResponseUtil.badArgument();
        }
        if (!ReUtil.isMatch(Validator.MOBILE, mobile)) {
            return ResponseUtil.badArgument();
        }

        Integer pid = address.getProvinceId();
        if (pid == null) {
            return ResponseUtil.badArgument();
        }
        if (ObjectUtil.isEmpty(regionBusService.queryById(pid))) {
            return ResponseUtil.badArgumentValue();
        }

        Integer cid = address.getCityId();
        if (cid == null) {
            return ResponseUtil.badArgument();
        }
        if (ObjectUtil.isEmpty(regionBusService.queryById(cid))) {
            return ResponseUtil.badArgumentValue();
        }

        Integer aid = address.getAreaId();
        if (aid == null) {
            return ResponseUtil.badArgument();
        }
        if (ObjectUtil.isEmpty(regionBusService.queryById(aid))) {
            return ResponseUtil.badArgumentValue();
        }

        String detailedAddress = address.getAddress();
        if (StringUtils.isEmpty(detailedAddress)) {
            return ResponseUtil.badArgument();
        }

        Boolean isDefault = address.getIsDefault();
        if (isDefault == null) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}

package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
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
        List<Map<String, Object>> addressVoList = new ArrayList<>(addressList.size());
        List<Region> regionList = regionBusService.getRegions();
        if (CollectionUtil.isEmpty(regionList)) {
            return ResponseUtil.ok();
        }
        for (UserAddress address : addressList) {
            Map<String, Object> addressVo = new HashMap<>(5);
            addressVo.put("id", address.getId());
            addressVo.put("name", address.getName());
            addressVo.put("mobile", address.getMobile());
            addressVo.put("isDefault", address.getIsDefault());
            String province = regionList.stream().filter(region -> region.getId().equals(address.getProvinceId())).findAny().map(Region::getName).orElse(null);
            String city = regionList.stream().filter(region -> region.getId().equals(address.getCityId())).findAny().map(Region::getName).orElse(null);
            String area = regionList.stream().filter(region -> region.getId().equals(address.getAreaId())).findAny().map(Region::getName).orElse(null);
            String addr = address.getAddress();
            String detailedAddress = province + city + area + " " + addr;
            addressVo.put("detailedAddress", detailedAddress);
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
        Map<Object, Object> data = new HashMap<>();
        data.put("id", address.getId());
        data.put("name", address.getName());
        data.put("provinceId", address.getProvinceId());
        data.put("cityId", address.getCityId());
        data.put("areaId", address.getAreaId());
        data.put("mobile", address.getMobile());
        data.put("address", address.getAddress());
        data.put("isDefault", address.getIsDefault());
        String provinceName = regionBusService.queryById(address.getProvinceId()).getName();
        data.put("provinceName", provinceName);
        String cityName = regionBusService.queryById(address.getCityId()).getName();
        data.put("cityName", cityName);
        String areaName = regionBusService.queryById(address.getAreaId()).getName();
        data.put("areaName", areaName);
        return ResponseUtil.ok(data);
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

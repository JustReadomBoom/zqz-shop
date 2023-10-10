package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.AddressToVo;
import com.zqz.shop.bean.admin.resp.PageQueryResp;
import com.zqz.shop.entity.UserAddress;
import com.zqz.shop.service.AdminAddressService;
import com.zqz.shop.service.business.UserAddressBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAddressServiceImpl
 * @Date: Created in 14:37 2023-8-25
 */
@Service
@Slf4j
public class AdminAddressServiceImpl implements AdminAddressService {
    @Autowired
    private UserAddressBusService addressBusService;


    @Override
    public Object doList(Integer userId, Integer page, Integer limit, Integer queryUserId, String name) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        PageQueryResp<AddressToVo> queryResp = new PageQueryResp<>();
        Page<UserAddress> addressPage = addressBusService.queryPage(page, limit, queryUserId, name);
        List<UserAddress> userAddressList = addressPage.getRecords();
        List<AddressToVo> addressVoList = new ArrayList<>(userAddressList.size());
        if (CollectionUtil.isEmpty(userAddressList)) {
            queryResp.setTotal(0L);
            queryResp.setItems(addressVoList);
            return ResponseUtil.ok(queryResp);
        }

        for (UserAddress userAddress : userAddressList) {
            AddressToVo addressVo = addressBusService.toVo(userAddress);
            addressVoList.add(addressVo);
        }
        queryResp.setTotal(addressPage.getTotalRow());
        queryResp.setItems(addressVoList);
        return ResponseUtil.ok(queryResp);
    }
}

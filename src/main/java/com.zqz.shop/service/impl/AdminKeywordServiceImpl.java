package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.admin.resp.PageQueryResp;
import com.zqz.shop.entity.SysKeyword;
import com.zqz.shop.service.AdminKeywordService;
import com.zqz.shop.service.business.SysKeywordBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminKeywordServiceImpl
 * @Date: Created in 11:05 2023-9-1
 */
@Service
@Slf4j
public class AdminKeywordServiceImpl implements AdminKeywordService {
    @Autowired
    private SysKeywordBusService keywordBusService;

    @Override
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String keyword, String url) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Page<SysKeyword> keywordPage = keywordBusService.queryPage(page, limit, keyword, url);
        PageQueryResp<SysKeyword> queryResp = new PageQueryResp<>();
        queryResp.setTotal(keywordPage.getTotalRow());
        queryResp.setItems(keywordPage.getRecords());
        return ResponseUtil.ok(queryResp);
    }

    @Override
    public Object doDelete(Integer adminUserId, SysKeyword keyword) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Integer id = keyword.getId();
        if (ObjectUtil.isEmpty(id)) {
            return ResponseUtil.badArgument();
        }
        int delete = keywordBusService.logicalDeleteById(id);
        if (delete <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doUpdateInfo(Integer adminUserId, SysKeyword keyword) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }

        String kw = keyword.getKeyword();
        if (StrUtil.isBlank(kw)) {
            return ResponseUtil.badArgument();
        }

        int update = keywordBusService.updateById(keyword);
        if (update <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doCreateInfo(Integer adminUserId, SysKeyword keyword) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        String kw = keyword.getKeyword();
        if (StrUtil.isBlank(kw)) {
            return ResponseUtil.badArgument();
        }
        int add = keywordBusService.add(keyword);
        if (add <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
}

package com.zqz.shop.service.business;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.common.Constant;
import com.zqz.shop.entity.SysKeyword;
import com.zqz.shop.mapper.SysKeywordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.zqz.shop.entity.table.SysKeywordTableDef.SYS_KEYWORD;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SysKeywordBusService
 * @Date: Created in 17:31 2023-8-18
 */
@Service
public class SysKeywordBusService {
    @Resource
    private SysKeywordMapper keywordMapper;

    public SysKeyword queryDefault() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(SYS_KEYWORD.IS_DEFAULT.eq(true))
                .and(SYS_KEYWORD.DELETED.eq(false));
        return keywordMapper.selectOneByQuery(wrapper);
    }

    public List<SysKeyword> queryHots() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(SYS_KEYWORD.IS_HOT.eq(true))
                .and(SYS_KEYWORD.DELETED.eq(false));
        return keywordMapper.selectListByQuery(wrapper);
    }

    public Page<SysKeyword> queryPage(Integer page, Integer limit, String keyword, String url) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select().and(Constant.WHERE_ONE_TO_ONE);
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.and(SYS_KEYWORD.KEYWORD.like(keyword));
        }
        if (StrUtil.isNotBlank(url)) {
            wrapper.and(SYS_KEYWORD.URL.like(url));
        }
        wrapper.and(SYS_KEYWORD.DELETED.eq(false))
                .orderBy(SYS_KEYWORD.ADD_TIME.desc());
        return keywordMapper.paginateWithRelations(page, limit, wrapper);
    }

    public int logicalDeleteById(Integer id) {
        return keywordMapper.logicalDeleteById(id);
    }

    public int updateById(SysKeyword keyword) {
        keyword.setUpdateTime(new Date());
        return keywordMapper.update(keyword);
    }

    public int add(SysKeyword keyword) {
        keyword.setAddTime(new Date());
        return keywordMapper.insertSelective(keyword);
    }
}

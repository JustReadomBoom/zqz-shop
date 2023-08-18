package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.SysKeyword;
import com.zqz.shop.mapper.SysKeywordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}

package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.SearchHistory;
import com.zqz.shop.mapper.SearchHistoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static com.zqz.shop.entity.table.SearchHistoryTableDef.SEARCH_HISTORY;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SearchBusService
 * @Date: Created in 17:28 2023-8-18
 */
@Service
public class SearchBusService {
    @Resource
    private SearchHistoryMapper searchHistoryMapper;

    public List<SearchHistory> queryByUserId(Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(SEARCH_HISTORY.USER_ID.eq(userId))
                .and(SEARCH_HISTORY.DELETED.eq(false));
        List<SearchHistory> searchHistoryList = searchHistoryMapper.selectListByQuery(wrapper);
        if (CollectionUtil.isNotEmpty(searchHistoryList)) {
            //根据userId去重
            return searchHistoryList.stream().collect(
                    collectingAndThen(
                            toCollection(() -> new TreeSet<>(Comparator.comparing(SearchHistory::getUserId))), ArrayList::new)
            );
        }
        return null;
    }
}

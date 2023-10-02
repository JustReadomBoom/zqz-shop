package com.zqz.shop.bean.resp;

import com.zqz.shop.entity.SearchHistory;
import com.zqz.shop.entity.SysKeyword;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SearchIndexResp
 * @Date: Created in 14:40 2023-10-2
 */
@Data
public class SearchIndexResp implements Serializable {
    private static final long serialVersionUID = -2387659692633167650L;

    private SysKeyword defaultKeyword;

    private List<SearchHistory> historyKeywordList;

    private List<SysKeyword> hotKeywordList;
}

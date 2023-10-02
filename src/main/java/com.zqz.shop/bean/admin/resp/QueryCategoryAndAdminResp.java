package com.zqz.shop.bean.admin.resp;

import com.zqz.shop.bean.admin.CatVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: QueryCategoryAndAdminResp
 * @Date: Created in 13:50 2023-10-2
 */
@Data
public class QueryCategoryAndAdminResp implements Serializable {
    private static final long serialVersionUID = 6926548567529263280L;

    private List<CatVo> categoryList;


    private List<ValueLabelResp> adminList;
}

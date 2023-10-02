package com.zqz.shop.bean.admin.resp;

import com.zqz.shop.bean.admin.CatVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CatAndBrandResp
 * @Date: Created in 14:04 2023-10-2
 */
@Data
public class CatAndBrandResp implements Serializable {
    private static final long serialVersionUID = 88184479305751048L;

    private List<CatVo> categoryList;

    private List<ValueLabelResp> brandList;
}

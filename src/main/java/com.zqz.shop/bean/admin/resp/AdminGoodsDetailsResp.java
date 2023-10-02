package com.zqz.shop.bean.admin.resp;

import com.zqz.shop.bean.GoodInfo;
import com.zqz.shop.bean.GoodsProductVo;
import com.zqz.shop.entity.GoodsAttribute;
import com.zqz.shop.entity.GoodsSpecification;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminGoodsDetailsResp
 * @Date: Created in 14:06 2023-10-2
 */
@Data
public class AdminGoodsDetailsResp implements Serializable {
    private static final long serialVersionUID = 4731465355290087721L;

    private GoodInfo goods;

    private List<GoodsSpecification> specifications;

    private List<GoodsProductVo> products;

    private List<GoodsAttribute> attributes;

    private Integer[] categoryIds;
}

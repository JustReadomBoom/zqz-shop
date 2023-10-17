package com.zqz.shop.bean.admin.req;

import com.zqz.shop.bean.GoodInfo;
import com.zqz.shop.bean.admin.GoodsAttributeUpdateVo;
import com.zqz.shop.bean.admin.GoodsProductUpdateVo;
import com.zqz.shop.bean.admin.GoodsSpecificationUpdateVo;
import com.zqz.shop.entity.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsUpdateReq
 * @Date: Created in 14:56 2023-9-4
 */
@Data
public class GoodsUpdateReq implements Serializable {
    private static final long serialVersionUID = 4649507593636055299L;

    private GoodInfo goods;

    private List<GoodsSpecificationUpdateVo> specifications;

    private List<GoodsAttributeUpdateVo> attributes;

    private List<GoodsProductUpdateVo> products;
}

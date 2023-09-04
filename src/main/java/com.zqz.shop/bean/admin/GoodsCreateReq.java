package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsCreateReq
 * @Date: Created in 16:41 2023-9-4
 */
@Data
public class GoodsCreateReq implements Serializable {

    private static final long serialVersionUID = -2166990937562339178L;

    private CreateGoodsVo goods;

    private List<GoodsSpecificationUpdateVo> specifications;

    private List<GoodsAttributeUpdateVo> attributes;

    private List<GoodsProductUpdateVo> products;
}

package com.zqz.shop.bean.resp;

import com.zqz.shop.bean.GoodInfo;
import com.zqz.shop.bean.GoodsProductVo;
import com.zqz.shop.bean.GoodsSpecificationVo;
import com.zqz.shop.entity.GoodsAttribute;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsQueryDetailResp
 * @Date: Created in 10:06 2023-9-15
 */
@Data
public class GoodsQueryDetailResp implements Serializable {
    private static final long serialVersionUID = -1870191332065696171L;

    private GoodInfo info;

    private List<GoodsSpecificationVo> specificationList;

    private List<GoodsProductVo> productList;

    private List<GoodsAttribute> attribute;

    private String shareImage;
}

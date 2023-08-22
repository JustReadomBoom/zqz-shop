package com.zqz.shop.bean;

import com.zqz.shop.entity.Brand;
import com.zqz.shop.entity.Cart;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于存储 品牌入驻商购物车商品的对象
 */
@Data
public class BrandCartGoods implements Serializable {

    private static final long serialVersionUID = -7908381028314100456L;

    private static final Integer DEFAULT_BRAND_ID = 1001000;

    private static final String DEFAULT_BRAND_COMMPANY = "琦智自营店";

    private static final String DEFAULT_BRAND_NAME = "琦智自营店";

    private Integer brandId;

    private String brandName;

    private String brandCommpany;

    private List<Cart> cartList;

    private BigDecimal bandGoodsTotalPrice;

    private BigDecimal bandFreightPrice;


    public static BrandCartGoods init(Brand brand) {
        BrandCartGoods bcg = new BrandCartGoods();
        if (brand != null) {
            bcg.setBrandId(brand.getId());
            bcg.setBrandCommpany(brand.getCommpany());
            bcg.setBrandName(brand.getName());
        } else {
            bcg.setBrandId(DEFAULT_BRAND_ID);
            bcg.setBrandCommpany(DEFAULT_BRAND_COMMPANY);
            bcg.setBrandName(DEFAULT_BRAND_NAME);
        }
        List<Cart> dtsCartList = new ArrayList<>();
        bcg.setCartList(dtsCartList);
        return bcg;
    }

}

package com.zqz.shop.bean.resp;

import com.zqz.shop.bean.BrandCartGoods;
import com.zqz.shop.entity.Cart;
import com.zqz.shop.entity.UserAddress;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartCheckoutResp
 * @Date: Created in 9:44 2023-9-15
 */
@Data
public class CartCheckoutResp implements Serializable {
    private static final long serialVersionUID = -4201748713516882660L;

    private Integer isMultiOrderModel;

    private BigDecimal goodsTotalPrice;

    private BigDecimal freightPrice;

    private List<BrandCartGoods> brandCartgoods;

    private List<Cart> checkedGoodsList;

    private Integer addressId;

    private UserAddress checkedAddress;

    private Integer couponId;

    private Integer availableCouponLength;

    private Integer grouponRulesId;

    private BigDecimal couponPrice;

    private BigDecimal orderTotalPrice;

    private BigDecimal actualPrice;

}

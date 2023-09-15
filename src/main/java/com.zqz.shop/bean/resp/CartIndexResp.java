package com.zqz.shop.bean.resp;

import com.zqz.shop.bean.BrandCartGoods;
import com.zqz.shop.entity.Cart;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartIndexResp
 * @Date: Created in 9:34 2023-9-15
 */
@Data
public class CartIndexResp implements Serializable {
    private static final long serialVersionUID = -3664780552930423819L;

    private Integer isMultiOrderModel;

    private List<BrandCartGoods> brandCartgoods;

    private List<Cart> cartList;

    private CartTotalVo cartTotal;





}

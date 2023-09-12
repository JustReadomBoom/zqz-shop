package com.zqz.shop.bean.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 购物车添加商品入参
 * @ClassName: AddCartProductReq
 * @Date: Created in 9:52 2023-8-24
 */
@Data
public class AddCartProductReq implements Serializable {
    private static final long serialVersionUID = 3992491091171135433L;

    @NotNull(message = "productId不能为空!")
    private Integer productId;

    @NotNull(message = "number不能为空!")
    private Short number;

    @NotNull(message = "goodsId不能为空!")
    private Integer goodsId;
}

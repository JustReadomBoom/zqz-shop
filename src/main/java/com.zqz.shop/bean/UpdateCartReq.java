package com.zqz.shop.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 更新购物车入参
 * @ClassName: UpdateCartReq
 * @Date: Created in 10:04 2023-8-24
 */
@Data
public class UpdateCartReq implements Serializable {
    private static final long serialVersionUID = 7731412114273419996L;

    @NotNull(message = "id不能为空!")
    private Integer id;

    @NotNull(message = "productId不能为空!")
    private Integer productId;

    @NotNull(message = "number不能为空!")
    private Short number;

    @NotNull(message = "goodsId不能为空!")
    private Integer goodsId;
}

package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsAttributeUpdateVo
 * @Date: Created in 14:53 2023-9-4
 */
@Data
public class GoodsAttributeUpdateVo implements Serializable {
    private static final long serialVersionUID = -5332437949045474601L;

    private Integer id;

    private Integer goodsId;

    private String attribute;

    private String value;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}

package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsProductUpdateVo
 * @Date: Created in 14:54 2023-9-4
 */
@Data
public class GoodsProductUpdateVo implements Serializable {
    private static final long serialVersionUID = 5785393389043051736L;

    private Integer id;

    private Integer goodsId;

    private List<String> specifications;

    private BigDecimal price;

    private Integer number;

    private String url;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}

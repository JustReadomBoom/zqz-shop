package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsSpecificationUpdateVo
 * @Date: Created in 14:52 2023-9-4
 */
@Data
public class GoodsSpecificationUpdateVo implements Serializable {
    private static final long serialVersionUID = -8277213931091205155L;

    private Integer id;

    private Integer goodsId;

    private String specification;

    private String value;

    private String picUrl;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}

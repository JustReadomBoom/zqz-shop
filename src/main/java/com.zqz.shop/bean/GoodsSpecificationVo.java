package com.zqz.shop.bean;

import com.zqz.shop.entity.GoodsSpecification;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsSpecificationVo
 * @Date: Created in 14:08 2023-8-18
 */
@Data
public class GoodsSpecificationVo implements Serializable {
    private static final long serialVersionUID = -2812774956327621438L;

    private String name;

    private List<GoodsSpecification> valueList;
}

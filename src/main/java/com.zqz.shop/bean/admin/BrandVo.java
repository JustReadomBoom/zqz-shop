package com.zqz.shop.bean.admin;

import com.zqz.shop.entity.Brand;
import lombok.Data;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: BrandVo
 * @Date: Created in 10:05 2023-9-1
 */
@Data
public class BrandVo extends Brand {

    private Integer[] categoryIds;
}

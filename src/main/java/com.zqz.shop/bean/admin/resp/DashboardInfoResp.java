package com.zqz.shop.bean.admin.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: DashboardInfoResp
 * @Date: Created in 10:17 2023-9-12
 */
@Data
public class DashboardInfoResp implements Serializable {
    private static final long serialVersionUID = -5708636844787516434L;

    private Integer userTotal;

    private Integer goodsTotal;

    private Integer productTotal;

    private Integer orderTotal;
}

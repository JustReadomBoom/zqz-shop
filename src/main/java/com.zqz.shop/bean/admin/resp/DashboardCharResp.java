package com.zqz.shop.bean.admin.resp;

import com.zqz.shop.bean.admin.CategorySellVo;
import com.zqz.shop.bean.admin.OrderAmtsVo;
import com.zqz.shop.bean.admin.UserOrderCntVo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: DashboardCharResp
 * @Date: Created in 10:15 2023-9-12
 */
@Data
public class DashboardCharResp implements Serializable {

    private static final long serialVersionUID = 1615725967080622607L;

    private UserOrderCntVo userOrderCnt;

    private OrderAmtsVo orderAmts;

    private CategorySellVo categorySell;


}

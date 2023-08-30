package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserOrderCntVo
 * @Date: Created in 20:04 2023-8-29
 */
@Data
public class UserOrderCntVo implements Serializable {

    private static final long serialVersionUID = 1257344238573152117L;

    /**日期数据*/
    private String[] dayData;

    /**每日用户新增量*/
    private int[] userCnt;

    /**每日订单量*/
    private int[] orderCnt;
}

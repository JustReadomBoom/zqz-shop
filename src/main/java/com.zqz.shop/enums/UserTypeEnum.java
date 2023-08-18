package com.zqz.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserTypeEnum
 * @Date: Created in 11:02 2023-8-11
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    COMM_USER((byte) 0, "普通用户"),
    VIP_USER((byte) 1, "VIP"),
    REGIONAL_AGENCY((byte) 2, "区域代理");

    private Byte level;
    private String desc;


    public static UserTypeEnum getInstance(Byte level2) {
        if (level2 != null) {
            for (UserTypeEnum tmp : UserTypeEnum.values()) {
                if (tmp.level.intValue() == level2.intValue()) {
                    return tmp;
                }
            }
        }
        return null;
    }
}

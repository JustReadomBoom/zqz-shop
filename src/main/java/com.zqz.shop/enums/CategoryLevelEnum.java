package com.zqz.shop.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CategoryLevelEnum
 * @Date: Created in 16:11 2023-9-11
 */
@Getter
@AllArgsConstructor
public enum CategoryLevelEnum {

    L1("L1"),
    L2("L2");
    private String level;


    public static CategoryLevelEnum match(String level) {
        if (StrUtil.isBlank(level)) {
            return null;
        }
        for (CategoryLevelEnum levelEnum : CategoryLevelEnum.values()) {
            if (level.equals(levelEnum.level)) {
                return levelEnum;
            }
        }
        return null;
    }
}

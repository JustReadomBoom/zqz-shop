package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AddressVo
 * @Date: Created in 14:45 2023-10-2
 */
@Data
public class AddressVo implements Serializable {
    private static final long serialVersionUID = -7118695499678838374L;

    private Integer id;

    private String name;

    private String mobile;

    private Boolean isDefault;

    private String detailedAddress;
}

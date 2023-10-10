package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AddressToVo
 * @Date: Created in 9:52 2023-10-10
 */
@Data
public class AddressToVo implements Serializable {
    private static final long serialVersionUID = 4512520207338121365L;

    private Integer id;

    private Integer userId;

    private String name;

    private String mobile;

    private Boolean isDefault;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private String address;

    private String province;

    private String city;

    private String area;

}

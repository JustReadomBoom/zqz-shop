package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AddressDetailVo
 * @Date: Created in 15:07 2023-10-2
 */
@Data
public class AddressDetailVo implements Serializable {

    private static final long serialVersionUID = 5631208978522953740L;

    private Integer id;

    private String name;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private String mobile;

    private String address;

    private Boolean isDefault;

    private String provinceName;

    private String cityName;

    private String areaName;




}

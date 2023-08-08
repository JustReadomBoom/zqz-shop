package com.zqz.shop.entity;


import com.mybatisflex.annotation.Table;
import lombok.Data;

@Data
@Table("region")
public class Region {
    private Integer id;

    private Integer pid;

    private String name;

    private Byte type;

    private Integer code;
}

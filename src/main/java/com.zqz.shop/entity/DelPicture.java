package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

@Data
@Table("del_picture")
public class DelPicture {

    private Integer id;

    private String picurl;


}

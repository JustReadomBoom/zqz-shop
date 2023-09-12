package com.zqz.shop.bean.admin.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminUpdateReq
 * @Date: Created in 15:08 2023-9-5
 */
@Data
public class AdminUpdateReq implements Serializable {
    private static final long serialVersionUID = 241516404448700545L;

    private Integer id;

    private String username;

    private String password;

    private String lastLoginIp;

    private Date lastLoginTime;

    private String avatar;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private List<String> roleIds;

    private String desc;

    private String tel;
}

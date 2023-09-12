package com.zqz.shop.bean.admin.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GetUserInfoResp
 * @Date: Created in 9:47 2023-9-12
 */
@Data
public class GetUserInfoResp implements Serializable {
    private static final long serialVersionUID = 5062177181053218389L;

    private String name;
    private String avatar;
    private Set<String> roles;
    private Set<String> perms;
}

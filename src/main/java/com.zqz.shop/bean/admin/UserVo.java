package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserVo
 * @Date: Created in 21:21 2023-8-31
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = -1787381889722463973L;

    private String nickname;

    private String avatar;

}

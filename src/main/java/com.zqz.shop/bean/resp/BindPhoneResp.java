package com.zqz.shop.bean.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: BindPhoneResp
 * @Date: Created in 9:38 2023-9-12
 */
@Data
public class BindPhoneResp implements Serializable {
    private static final long serialVersionUID = -2044093377280470954L;

    private String phone;
}

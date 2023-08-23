package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 绑定手机号入参
 * @ClassName: BindPhoneReq
 * @Date: Created in 17:26 2023-8-23
 */
@Data
public class BindPhoneReq implements Serializable {
    private static final long serialVersionUID = -4235975413691769883L;

    private String encryptedData;

    private String iv;
}

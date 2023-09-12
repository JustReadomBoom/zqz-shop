package com.zqz.shop.bean.admin.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: ValueLabelResp
 * @Date: Created in 10:04 2023-9-12
 */
@Data
public class ValueLabelResp implements Serializable {
    private static final long serialVersionUID = -2809388129697698198L;

    private Integer value;

    private String label;
}

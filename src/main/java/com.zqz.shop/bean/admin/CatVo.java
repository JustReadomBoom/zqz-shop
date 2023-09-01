package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CatVo
 * @Date: Created in 11:03 2023-8-31
 */
@Data
public class CatVo implements Serializable {
    private static final long serialVersionUID = -5621651957233612073L;

    private Integer value = null;

    private String label = null;

    private List children = null;
}

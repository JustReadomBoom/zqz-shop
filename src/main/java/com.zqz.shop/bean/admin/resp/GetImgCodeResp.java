package com.zqz.shop.bean.admin.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GetImgCodeResp
 * @Date: Created in 9:45 2023-9-12
 */
@Data
public class GetImgCodeResp implements Serializable {
    private static final long serialVersionUID = -7326627257106343500L;

    private String uuid;

    private String img;
}

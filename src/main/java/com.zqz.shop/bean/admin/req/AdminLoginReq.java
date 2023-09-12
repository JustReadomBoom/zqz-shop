package com.zqz.shop.bean.admin.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 登录入参
 * @ClassName: AdminLoginReq
 * @Date: Created in 9:59 2023-8-25
 */
@Data
public class AdminLoginReq implements Serializable {
    private static final long serialVersionUID = 1761502921799641518L;

    @JsonProperty("username")
    @NotBlank(message = "username不能为空")
    private String userName;

    @JsonProperty("password")
    @NotBlank(message = "password不能为空")
    private String passWord;

    @JsonProperty("code")
    @NotBlank(message = "code不能为空")
    private String code;

    @JsonProperty("uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

}

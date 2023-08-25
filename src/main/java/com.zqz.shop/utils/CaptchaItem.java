package com.zqz.shop.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: ZQZ
 * @Description: 验证码实体类，用于缓存验证码发送
 * @ClassName: CaptchaItem
 * @Date: Created in 11:50 2023-8-11
 */
@Data
public class CaptchaItem {

	private String flagUid;
	private String code;
	private LocalDateTime expireTime;
}

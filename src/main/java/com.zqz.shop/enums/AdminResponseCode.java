package com.zqz.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: ZQZ
 * @Description: 返回码
 * @ClassName: AdminResponseCode
 * @Date: Created in 10:37 2023-8-11
 */
@Getter
@AllArgsConstructor
public enum AdminResponseCode {

	ADMIN_INVALID_NAME(600, "管理员名称不符合规定"),
	ADMIN_INVALID_PASSWORD(601, "管理员密码长度不能小于6"),
	ADMIN_NAME_EXIST(602, "管理员已经存在"),
	ADMIN_INVALID_ACCOUNT_OR_PASSWORD(605, "用户帐号或密码不正确"),
	ADMIN_LOCK_ACCOUNT(606, "用户帐号已锁定不可用"),
	ADMIN_INVALID_AUTH(607, "认证失败"),
	GOODS_UPDATE_NOT_ALLOWED(610, "商品已经在订单或购物车中，不能修改"),
	GOODS_NAME_EXIST(611, "商品名已经存在"),
	ORDER_CONFIRM_NOT_ALLOWED(620, "当前订单状态不能确认收货"),
	ORDER_REFUND_FAILED(621, "当前订单状态不能退款"),
	ORDER_REPLY_EXIST(622, "订单商品已回复！"),
	ADMIN_INVALID_OLD_PASSWORD(623, "原始密码不正确！"),
	ROLE_NAME_EXIST(640, "角色已经存在"),
	ROLE_SUPER_SUPERMISSION(641, "当前角色的超级权限不能变更"),
	ARTICLE_NAME_EXIST(642,"公告或通知文章已经存在"),
	AUTH_CAPTCHA_FREQUENCY(643,"验证码请求过于频繁"),
	AUTH_CAPTCHA_ERROR(644,"验证码错误"),
	AUTH_CAPTCHA_EXPIRED(645,"验证码过期"),
	AUTH_ADMIN_NOT_EXIST(646, "用户不存在"),
	AUTH_PASSWORD_ERROR(647, "密码错误"),
	AUTH_OPE_ERROR(648, "操作失败");

	private final Integer code;
	private final String desc;


	public static AdminResponseCode getInstance(Integer code) {
		if (code != null) {
			for (AdminResponseCode tmp : AdminResponseCode.values()) {
				if (tmp.code.intValue() == code.intValue()) {
					return tmp;
				}
			}
		}
		return null;
	}
}

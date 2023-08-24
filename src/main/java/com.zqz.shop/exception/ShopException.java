package com.zqz.shop.exception;

/**
 * @Author: ZQZ
 * @Description: 自定义异常
 * @ClassName: ShopException
 * @Date: Created in 9:14 2023-8-24
 */
public class ShopException extends RuntimeException {

    private static final long serialVersionUID = 3473429666472854852L;

    public ShopException() {
    }

    public ShopException(String message) {
        super(message);
    }

}

package com.zqz.shop.exception;

import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @Author: ZQZ
 * @Description: 异常处理器
 * @ClassName: ShopExceptionHandler
 * @Date: Created in 9:12 2023-8-24
 */
@RestControllerAdvice
@Slf4j
public class ShopExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        //区分是否为自定义异常
        if (e instanceof ShopException) {
            log.error("自定义异常:{}", e.getMessage());
            return ResponseUtil.fail(e.getMessage());
        } else {
            log.error("未知异常:{}", e.getMessage());
            e.printStackTrace();
            return ResponseUtil.fail("未知异常，请查看控制台日志！");
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseUtil.fail(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

}

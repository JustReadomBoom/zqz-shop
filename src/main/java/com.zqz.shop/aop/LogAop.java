package com.zqz.shop.aop;

import cn.hutool.json.JSONUtil;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZQZ
 * @Description: 日志切面
 * @ClassName: LogAop
 * @Date: Created in 17:09 2022-9-7
 */
@Aspect
@Component
@Slf4j
public class LogAop {

    @Pointcut("execution(* com.zqz.shop.controller..*.*(..))")
    public void logAspect() {

    }

    @Before("logAspect()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        StringBuffer requestUrl = request.getRequestURL();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();
        log.info("【请求路径】: {}", requestUrl);
        log.info("【请求方法】: {}", method);
        log.info("【请求地址】: {}", remoteAddr);
        log.info("【请求方法】: {}", name);

        Object[] args = joinPoint.getArgs();
        for (Object o : args) {
            log.info("【请求参数】: {}", o);
        }
    }


    @After("logAspect()")
    public void doAfter(JoinPoint joinPoint) {

    }

    @Around("logAspect()")
    public Object deAround(ProceedingJoinPoint joinPoint) {
        log.info("================开始执行==================");
        try {
            Object proceed = joinPoint.proceed();
            log.info("【请求响应】: {}", JSONUtil.toJsonStr(proceed));
            log.info("================执行完毕==================");
            return proceed;
        } catch (Throwable e) {
            log.error("******执行异常:{}", e.getMessage(), e);
            return ResponseUtil.fail(e.getMessage());
        }
    }
}

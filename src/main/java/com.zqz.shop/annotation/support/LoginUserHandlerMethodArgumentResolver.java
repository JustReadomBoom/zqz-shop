package com.zqz.shop.annotation.support;

import com.zqz.shop.annotation.LoginUser;
import com.zqz.shop.utils.UserTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: ZQZ
 * @Description: 登录拦截
 * @ClassName: LoginUserHandlerMethodArgumentResolver
 * @Date: Created in 10:08 2023-8-11
 */
@Slf4j
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	public static final String LOGIN_TOKEN_KEY = "X-Dts-Token";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(Integer.class)
				&& parameter.hasParameterAnnotation(LoginUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request,
                                  WebDataBinderFactory factory) throws Exception {

		String token = request.getHeader(LOGIN_TOKEN_KEY);
		if (token == null || token.isEmpty()) {
			return null;
		}
		Integer userToken = UserTokenManager.getUserId(token);
		log.info("login handler token = {}", userToken);
		return userToken;
	}
}

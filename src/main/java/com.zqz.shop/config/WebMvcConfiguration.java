package com.zqz.shop.config;

import com.zqz.shop.annotation.support.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description: WebMvc配置
 * @ClassName: WebMvcConfiguration
 * @Date: Created in 10:08 2023-8-11
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
	}
}

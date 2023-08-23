package com.zqz.shop.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZQZ
 * @Description: 微信配置
 * @ClassName: WxConfig
 * @Date: Created in 10:08 2023-8-11
 */
@Configuration
public class WxConfig {

	@Value("${wx.app.id}")
	private String appId;

	@Value("${wx.app.secret}")
	private String appSecret;

	@Value("${wx.mch.id}")
	private String mchId;

	@Value("${wx.mch.key}")
	private String mchKey;

	@Value("${wx.notify.url}")
	private String notifyUrl;

	@Value("${wx.key.path}")
	private String keyPath;


	@Bean
	public WxMaConfig wxMaConfig() {
		WxMaInMemoryConfig config = new WxMaInMemoryConfig();
		config.setAppid(appId);
		config.setSecret(appSecret);
		return config;
	}

	@Bean
	public WxMaService wxMaService(WxMaConfig maConfig) {
		WxMaService service = new WxMaServiceImpl();
		service.setWxMaConfig(maConfig);
		return service;
	}

	@Bean
	public WxPayConfig wxPayConfig() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(appId);
		payConfig.setMchId(mchId);
		payConfig.setMchKey(mchKey);
		payConfig.setNotifyUrl(notifyUrl);
		payConfig.setKeyPath(keyPath);
		payConfig.setTradeType("JSAPI");
		payConfig.setSignType("MD5");
		return payConfig;
	}

	@Bean
	public WxPayService wxPayService(WxPayConfig payConfig) {
		WxPayService wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig);
		return wxPayService;
	}
}

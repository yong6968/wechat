/*
 * Copyright (C) 2015 ShenZhen HeShiDai Co.,Ltd All Rights Reserved.
 * 未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 * 版权所有深圳合时代金融服务有限公司 www.heshidai.com.
 */
package com.github.binarywang.demo.spring.controller;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.binarywang.demo.spring.service.WeixinService;

@Controller
public class WxOauth2Controller {
	  @Autowired
	  private WeixinService wxService;
	  
	  private final Logger logger = LoggerFactory.getLogger(this.getClass());
	  
	  @RequestMapping("/wechat/oauth2/authorize")
	  public String authorize() throws WxErrorException {
		  	String redirectURI = "http://www.linzyweb.com/weixin/wechat/oauth2/access_token";
	        String scope = "snsapi_userinfo"; //snsapi_userinfo需要用户手动同意 ;snsapi_base静默授权并自动跳转到回调页
	        String state = "test";
	        String authorizationUrl  =  this.wxService.oauth2buildAuthorizationUrl(redirectURI, scope, state);
	        return "redirect:" + authorizationUrl;
	  }
	  
	  @RequestMapping("/wechat/oauth2/qrconnect")
	  public String qrconnect() throws WxErrorException{
		  String redirectURI = "http://www.linzyweb.com/weixin/wechat/oauth2/access_token";
		  String scope = "snsapi_login";
		  String state = "test";
		  String buildQrConnectUrl = this.wxService.buildQrConnectUrl(redirectURI, scope, state);
		  return "redirect:" + buildQrConnectUrl;
	  }
	  
	  @RequestMapping("/wechat/oauth2/access_token")
	  public WxMpOAuth2AccessToken access_token(String code,String state) throws WxErrorException{
		  this.logger.debug("\n oauth2/authorize请求参数：{},{}", code,state);
		  WxMpOAuth2AccessToken token = this.wxService.oauth2getAccessToken(code);
		  //查询用户是否已经注册，假如没注册提示领取奖品
		  this.logger.debug("\n oauth2/authorize响应参数：{}", token);
		  return token;
	  }
	  
	  
}

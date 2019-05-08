package com.huowolf.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Binary Wang
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
  private WxPayProperties properties;

  @Autowired
  public WxPayConfiguration(WxPayProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public WxPayService wxService() {
    WxPayConfig payConfig = new WxPayConfig();
    payConfig.setAppId(StringUtils.trimToNull(properties.getAppId()));
    payConfig.setMchId(StringUtils.trimToNull(properties.getMchId()));
    payConfig.setMchKey(StringUtils.trimToNull(properties.getMchKey()));
    payConfig.setNotifyUrl(StringUtils.trimToNull(properties.getNotifyUrl()));

    WxPayService wxPayService = new WxPayServiceImpl();
    wxPayService.setConfig(payConfig);
    return wxPayService;
  }

}

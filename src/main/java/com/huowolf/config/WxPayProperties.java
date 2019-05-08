package com.huowolf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * wxpay pay properties
 *
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayProperties {

  /**
   * 设置微信公众号或者小程序等的appid
   */
  private String appId;

  /**
   * 微信支付商户号
   */
  private String mchId;

  /**
   * 微信支付商户密钥
   */
  private String mchKey;


  /**
   * 回调地址
   */
  private String notifyUrl;
}

package com.huowolf.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.huowolf.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author huowolf
 * @date 2019/5/8
 * @description Native Pay
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private WxPayService wxService;

    public static final IdGenerator ID_GENERATOR = IdGenerator.INSTANCE;

    @GetMapping(value = "/create",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] createOrder() throws WxPayException {
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setBody("大爱平江慈善捐助");
        request.setOutTradeNo(ID_GENERATOR.nextId());
        //金额
        request.setTotalFee(BaseWxPayRequest.yuanToFen(0.01+""));
        request.setSpbillCreateIp("127.0.0.1");
        request.setTradeType(WxPayConstants.TradeType.NATIVE);
        //商品Id
        request.setProductId(111+"");

        WxPayNativeOrderResult result = wxService.createOrder(request);
        String codeUrl = result.getCodeUrl();

        return wxService.createScanPayQrcodeMode2(codeUrl, null, null);
    }


    @PostMapping("/notify")
    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);
        // TODO
        //商户系统的订单号
        System.out.println(notifyResult.getOutTradeNo());

        //微信支付订单号(微信支付系统生成的)
        System.out.println(notifyResult.getTransactionId());

        //金额
        System.out.println(notifyResult.getTotalFee());

        return WxPayNotifyResponse.success("成功");
    }
}

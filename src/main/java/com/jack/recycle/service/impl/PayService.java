package com.jack.recycle.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.jack.recycle.config.AlipayConfig;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class PayService {


    public String aliPay(String WIDout_trade_no, String WIDsubject, String WIDtotal_amount) throws AlipayApiException, UnsupportedEncodingException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl,
                AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json",
                AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);


        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        System.out.println(AlipayConfig.notify_url);
        System.out.println(AlipayConfig.return_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = WIDout_trade_no;
        //付款金额，必填
        String total_amount = WIDtotal_amount;
        //订单名称，必填
        String subject = WIDsubject;
        //
        String body ="测试商品";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\""
                + total_amount + "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println(result);
        return result;
    }

    public String returnUrl(HttpSession session, Map params) throws AlipayApiException, UnsupportedEncodingException {

        return null;
//        User user = (User)session.getAttribute("user");
//        System.out.println(user);
//        System.out.println("11111111111111111111111111111111111111111");
//
//        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
//        if(signVerified){
//
//            //支付成功，根据当前支付订单获取当前用户
//            String out_trade_no=params.get("out_trade_no").toString();
//            if(!out_trade_no.equals("")&&out_trade_no!=null){
//
//            }
//            //返回当前用户
//            return "success";
//        }
//        return "";
    }



}

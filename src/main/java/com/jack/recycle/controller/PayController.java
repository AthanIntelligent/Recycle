package com.jack.recycle.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jack.recycle.config.AlipayConfig;
import com.jack.recycle.model.PO.TransactionGoods;
import com.jack.recycle.service.TransactionGoodsService;
import com.jack.recycle.service.TransactionService;
import com.jack.recycle.service.impl.PayService;
import com.jack.recycle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private TransactionService tranService;
    @Autowired
    private TransactionGoodsService tranGoodsService;
    @Autowired
    private PayService payService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/alipay")
    public String alipay(@RequestBody List<TransactionGoods> goodsList, @CookieValue("XM_TOKEN") String cookie, HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException, AlipayApiException {
        String name = "";
        Double amount = 0.0;
        Map<String,String> map = new HashMap<>();
        String WIDout_trade_no = UUID.randomUUID().toString(); // 订单id
        for(TransactionGoods goods : goodsList){
            amount += goods.getAmount();
            if(!map.containsKey(goods.getGoodsName())){
                map.put(goods.getGoodsName(),goods.getGoodsName());
                name += goods.getGoodsName();
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("goodsList",goodsList);
        return  payService.aliPay(WIDout_trade_no, name, amount.toString());

    }

    @RequestMapping("/paysuccess")
    public void paysuccess(HttpServletRequest request, @CookieValue("XM_TOKEN") String cookie, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        List<TransactionGoods> goodsList = (List<TransactionGoods>) session.getAttribute("goodsList");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
       boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        //商户订单号
        String order_id = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
        if(goodsList != null){
            try{
                String userId = UserUtils.getCurrUserInfo().getUuid();
//                transactionOrderService.addOrder(goodsList,order_id, userId);
            }catch (Exception ex){
                ex.getMessage();
            }
        }
        response.sendRedirect("http://localhost:8080/");
    }
}

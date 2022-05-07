package com.jack.recycle.controller;

import com.jack.recycle.model.Transaction;
import com.jack.recycle.model.TransactionGoods;
import com.jack.recycle.model.VO.TransactionAndGoods;
import com.jack.recycle.service.GoodsService;
import com.jack.recycle.service.TransactionGoodsService;
import com.jack.recycle.service.TransactionService;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TransactionGoodsService transactionGoodsService;

    @PostMapping(value = "/addTransaction")
    public Result addTransaction(@RequestBody TransactionAndGoods transactionAndGoods){
        Transaction transaction = transactionAndGoods.getTransaction();
        transaction.setUuid(UUID.randomUUID().toString());
        //传用户真实姓名过来转成uuid
        String userId = userService.getUuidByRealName(transaction.getUserId());
        transaction.setUserId(userId);
        //添加交易记录时，基站名称和法人姓名 自动显示且按钮禁用，点击保存时，传id，单独写一个接口根据当前登录人id返回人员和基站
//        transaction.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        String formatDate = DateUtils.getFormatDate("yyyy-MM-dd HH:mm");
        transaction.setTransactionTime(formatDate);

        List<String> goodsIdsList = new ArrayList<>();
        //获取交易物品list
        List<TransactionGoods> transactionGoodsList = transactionAndGoods.getTransactionGoodsList();
        for (TransactionGoods tran:transactionGoodsList) {
            tran.setUuid(UUID.randomUUID().toString());
            tran.setTransactionId(transaction.getUuid());
            transactionGoodsService.addTransactionGoods(tran);
            goodsIdsList.add(tran.getUuid());
        }
        String goodsIds = String.join(",", goodsIdsList);
        transaction.setTransactionGoodsId(goodsIds);
        transactionService.addTransaction(transaction);
        return new Result(StatusCode.OK, "OK", null);
    }


    @PostMapping(value = "/dirTransaction")
    public Result dirTransaction(@RequestBody Transaction transaction){
        //用户姓名转换成id，如果输入的用户姓名不存在，提示"该用户不存在"，不用这个查询条件
        if (!StringHelper.isNullOrEmptyString(transaction.getUserId())) {
            String uuidByRealName = userService.getUuidByRealName(transaction.getUserId());
            if (!StringUtils.isEmpty(uuidByRealName))
                transaction.setUserId(uuidByRealName);
            else
                return new Result(StatusCode.INTERNAL_SERVER_ERROR, "该用户不存在", transaction.getUserId());
        }
        List<Transaction> transactions = transactionService.dirTransaction(transaction);
        for (Transaction tr:transactions) {
            String realName = userService.getRealNameByUuid(tr.getUserId());
            tr.setUserId(realName);
            String realName2 = userService.getRealNameByUuid(tr.getStationLegal());
            tr.setStationLegal(realName2);
        }
//        List<TransactionAndGoods> result = new ArrayList<>();
//        for (Transaction tran:transactions) {
//            TransactionGoods transactionGoods = new TransactionGoods();
//            transactionGoods.setTransactionId(tran.getUuid());
//            List<TransactionGoods> transactionGoodsList = transactionGoodsService.dirtransactionGoods(transactionGoods);
//
//            TransactionAndGoods transactionAndGoods = new TransactionAndGoods();
//            transactionAndGoods.setTransaction(tran);
//            transactionAndGoods.setTransactionGoodsList(transactionGoodsList);
//            result.add(transactionAndGoods);
//        }
        return new Result(StatusCode.OK, "OK", transactions);
    }

    @GetMapping(value = "/getTransactionGoods")
    public Result getTransactionGoods(@RequestParam("transactionId") String transactionId) {
        TransactionGoods transactionGoods = new TransactionGoods();
        transactionGoods.setTransactionId(transactionId);
        List<TransactionGoods> transactionGoodsList = transactionGoodsService.dirTransactionGoods(transactionGoods);
        for (TransactionGoods tr:transactionGoodsList) {
            String goodsName = goodsService.getGoods(tr.getGoodsId()).getGoodsName();
            tr.setGoodsId(goodsName);
        }
        return new Result(StatusCode.OK, "OK", transactionGoodsList);
    }
}

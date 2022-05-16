package com.jack.recycle.controller;

import com.jack.recycle.model.Transaction;
import com.jack.recycle.model.VO.DayAndMoney;
import com.jack.recycle.model.VO.TransactionAndGoods;
import com.jack.recycle.service.TransactionGoodsService;
import com.jack.recycle.service.TransactionService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.jack.recycle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/userandstationtransaction")
public class UserAndStationTransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionGoodsService transactionGoodsService;

    /*
    基站人员获取当月每天支出
     */
    @GetMapping(value = "/dirUandSTransaction")
    public Result dirUandSTransaction() {

        String formatDate = DateUtils.getFormatDate("yyyy-MM");
        Transaction transaction = new Transaction();
        transaction.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        transaction.setTransactionTime(formatDate);
        //获取当前月份该基站的所有交易记录
        List<Transaction> transactions = transactionService.dirUandSTransaction(transaction);
        List<DayAndMoney> monthTransactionList = new ArrayList<>();
        Map<String, Double> map = new HashMap<>();
        for (Transaction tr:transactions) {
            String day = tr.getTransactionTime().substring(0, 10);
            if (!map.containsKey(day))
                map.put(day,tr.getAllMoney());
            else {
                Double mmm = map.get(day);
                mmm += tr.getAllMoney();
                map.replace(day,mmm);
            }

        }
        //返回当月每天收益 支出
        return new Result(StatusCode.OK, "OK", null);
    }

}

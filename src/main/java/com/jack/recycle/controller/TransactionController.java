package com.jack.recycle.controller;

import com.jack.recycle.model.Reservation;
import com.jack.recycle.model.Transaction;
import com.jack.recycle.model.VO.TransactionAndGoods;
import com.jack.recycle.service.*;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.jack.recycle.utils.UserUtils;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "*", maxAge = 3600)
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

    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/addTransaction")
    public Result addTransaction(@RequestBody TransactionAndGoods transactionAndGoods) throws Exception {
        Reservation reservation = transactionAndGoods.getReservation();
        Transaction transaction = new Transaction();
        transaction.setUuid(UUID.randomUUID().toString());
        //传用户真实姓名过来转成uuid
        String userId = userService.getUuidByRealName(reservation.getAppointmentHolder());
        transaction.setUserId(userId);
        transaction.setStationId(reservation.getAppointmentStation());
        transaction.setTransactionTime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm"));
        transaction.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        transaction.setAllMoney(transactionAndGoods.getAllMoney());
        transaction.setPayFlag(transactionAndGoods.getPayFlag());

        List<String> goodsIdsList = new ArrayList<>();
        //获取交易物品list
        List<com.jack.recycle.model.PO.TransactionGoods> transactionGoodsList = transactionAndGoods.getTransactionGoodsList();
        for (com.jack.recycle.model.PO.TransactionGoods tr: transactionGoodsList) {
            com.jack.recycle.model.TransactionGoods transactionGoods = new com.jack.recycle.model.TransactionGoods();
            transactionGoods.setUuid(UUID.randomUUID().toString());
            transactionGoods.setTransactionId(transaction.getUuid());
            transactionGoods.setGoodsId(tr.getUuid());
            transactionGoods.setWeight(tr.getGoodsWeight());
            transactionGoods.setMoney(tr.getGoodsPrice());
            transactionGoods.setUnit(tr.getGoodsUnit());
            transactionGoodsService.addTransactionGoods(transactionGoods);
            goodsIdsList.add(transactionGoods.getUuid());
        }

        String goodsIds = String.join(",", goodsIdsList);
        transaction.setTransactionGoodsId(goodsIds);
        transactionService.addTransaction(transaction);
        //交易完成之后，修改预约表的到访状态为已签到
        reservation.setIsCome("已签到");
        return new Result(StatusCode.OK, "OK", reservationService.updReservation(reservation));
    }


    @PostMapping(value = "/dirTransaction")
    public Result dirTransaction(@RequestBody Transaction transaction) throws Exception {
        if (UserUtils.getCurrUserInfo().getUserType().equals("2")) {
            transaction.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
            //用户姓名转换成id，如果输入的用户姓名不存在，提示"该用户不存在"，不用这个查询条件
            if (!StringHelper.isNullOrEmptyString(transaction.getUserId())) {
                String uuidByRealName = userService.getUuidByRealName(transaction.getUserId());
                if (!StringUtils.isEmpty(uuidByRealName))
                    transaction.setUserId(uuidByRealName);
                else
                    return new Result(StatusCode.INTERNAL_SERVER_ERROR, "该用户不存在", transaction.getUserId());
            }
        }
        if (UserUtils.getCurrUserInfo().getUserType().equals("1"))
            transaction.setUserId(UserUtils.getCurrUserInfo().getUuid());
        List<Transaction> transactions = transactionService.dirTransaction(transaction);
        for (Transaction tr : transactions) {
            String realName = userService.getRealNameByUuid(tr.getUserId());
            tr.setUserId(realName);
            String realName2 = userService.getRealNameByUuid(tr.getStationLegal());
            tr.setStationLegal(realName2);
        }
        return new Result(StatusCode.OK, "OK", transactions);
    }

    @GetMapping(value = "/getTransactionGoods")
    public Result getTransactionGoods(@RequestParam("transactionId") String transactionId) {
        com.jack.recycle.model.TransactionGoods transactionGoods = new com.jack.recycle.model.TransactionGoods();
        transactionGoods.setTransactionId(transactionId);
        List<com.jack.recycle.model.TransactionGoods> transactionGoodsList = transactionGoodsService.dirTransactionGoods(transactionGoods);
        for (com.jack.recycle.model.TransactionGoods tr : transactionGoodsList) {
            String goodsName = goodsService.getGoods(tr.getGoodsId()).getGoodsName();
            tr.setGoodsId(goodsName);
        }
        return new Result(StatusCode.OK, "OK", transactionGoodsList);
    }
}

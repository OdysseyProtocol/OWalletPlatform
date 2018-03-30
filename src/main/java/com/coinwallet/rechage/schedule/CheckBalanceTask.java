package com.coinwallet.rechage.schedule;

import com.coinwallet.common.util.Constants;
import com.coinwallet.common.web3j.bean.TransactionVerificationInfo;
import com.coinwallet.common.web3j.transaction.OWalletTransaction;
import com.coinwallet.rechage.dao.TransactionOrderMapper;
import com.coinwallet.rechage.entity.*;
import com.coinwallet.rechage.service.CheckRechargeOrderOnNodeService;
import com.coinwallet.rechage.service.CheckRechargeOrderOnScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckBalanceTask {

    @Autowired
    private CheckRechargeOrderOnScanService checkRechargeOrderOnScanService;

    @Autowired
    private TransactionOrderMapper transactionOrderMapper;
    @Autowired
    private CheckRechargeOrderOnNodeService checkRechargeOrderOnNodeService;


    protected Logger logger = LoggerFactory.getLogger(CheckBalanceTask.class);


    @Scheduled(cron = "0 */2 * * * ?")
    public void checkOrderStausFromNode() {
        List<TransactionOrder> transactionOrders = transactionOrderMapper.selectUnConfirmOrder(Constants.NODE_UNCONFIRM_ROW);
        if (transactionOrders != null) {
            for (TransactionOrder transactionOrder : transactionOrders) {
                try {
                    TransactionVerificationInfo verificationInfo = OWalletTransaction.doubleVerifyTransaction(transactionOrder.getTxHash());
                    checkRechargeOrderOnNodeService.confirmOrder(transactionOrder, verificationInfo);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }

            }

        }


    }

//    /**
//     * 定时任务走scan检测所有处于peeding中的订单(每次检测10条)
//     * 类型1：用户充值成功,修改用户余额,记录充值日志
//     * 类型2：给用户用户充值gas成功,提币到总账,记录给用户充值gas日志
//     * 类型3：提币到总账成功,记录给用户提币日志
//     */
//    @Scheduled(cron = "0 */10 * * * ?")
//    public void checkOrderStausFromScan() {
//        List<TransactionOrder> transactionOrders = transactionOrderMapper.selectUnConfirmOrder(Constants.SCAN_UNCONFIRM_ROW);
//        if (transactionOrders != null) {
//            for (TransactionOrder transactionOrder : transactionOrders) {
//                try {
//                    TransactionVerificationInfo verificationInfo = OWalletTransaction.verifyTransaction(transactionOrder.getTxHash());
//                    checkRechargeOrderOnNodeService.confirmOrder(transactionOrder, verificationInfo);
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }
//
//            }
//
//        }
//
//
//    }


    /**
     * 从最近扫的一个区块号到最新区块号减去12 扫描区块之间的记录
     */
    @Scheduled(cron = "0 */3 * * * ?")
    public void scanBlockTranscation() {
        checkRechargeOrderOnScanService.scanBlockTransaction();
    }
}
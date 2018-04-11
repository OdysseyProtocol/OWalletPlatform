package com.coinwallet.rechage.service;

import com.coinwallet.common.util.Constants;
import com.coinwallet.rechage.dao.TransactionOrderMapper;
import com.coinwallet.rechage.dao.UserCoinBalanceMapper;
import com.coinwallet.rechage.dao.UserCoinLogMapper;
import com.coinwallet.rechage.dao.UserWalletInfoMapper;
import com.coinwallet.rechage.entity.TransactionOrder;
import com.coinwallet.rechage.entity.UserCoinBalance;
import com.coinwallet.rechage.entity.userCoinLog;
import com.coinwallet.rechage.entity.UserWalletInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fly on 18/3/23.
 */
@Service
public class AffirmRechargeToGatherService {
    @Autowired
    private UserWalletInfoMapper userWalletInfoMapper;
    @Autowired
    private UserCoinBalanceMapper userCoinBalanceMapper;
    @Autowired
    private TransactionOrderMapper transactionOrderMapper;
    @Autowired
    private UserCoinLogMapper userCoinLogMapper;
    @Autowired
    private CheckSuccessRechargeOrderService checkSuccessRechargeOrderService;
    public void affirmRechargeCoin(TransactionOrder transactionOrder) {
        UserWalletInfo userWalletInfo = userWalletInfoMapper.selectWalletInfoByAddress(transactionOrder.getFromAddress());
        UserCoinBalance userCoinBalance = userCoinBalanceMapper.selectByUserIdAndMerchantInfoId(userWalletInfo.getUserid(), userWalletInfo.getMerchantId());
        int res = checkSuccessRechargeOrderService.changeTransactionSuccess(transactionOrder);
        if (res > 0) {
            userCoinLog userCoinLog = new userCoinLog();
            userCoinLog.setCoinId(userCoinBalance.getCoinId());
            userCoinLog.setCoinName(userCoinBalance.getCoinName());
            userCoinLog.setUserid(userCoinBalance.getUserid());
            userCoinLog.setMerchantId(userCoinBalance.getMerchantId());
            userCoinLog.setChangeNum(transactionOrder.getCoinNum());
            userCoinLog.setOrderTxHash(transactionOrder.getTxHash());
            userCoinLog.setChangeType(Constants.USER_COIN_COLLECT);
            userCoinLog.setCreateTime(new Date());
            int i = userCoinLogMapper.insertSelective(userCoinLog);
            UserCoinBalance updateUserCoinBalance = new UserCoinBalance();
            updateUserCoinBalance.setId(userCoinBalance.getId());
            BigDecimal coinNum = userCoinBalance.getCoinBalance().subtract(transactionOrder.getCoinNum());
            BigDecimal coinBalance = coinNum.compareTo(new BigDecimal("0")) < 0 ? new BigDecimal("0") : coinNum;
            updateUserCoinBalance.setCoinBalance(coinBalance);
            updateUserCoinBalance.setTransferStatus((byte) Constants.USER_TRANS_NORMAL);
            userCoinBalanceMapper.updateByPrimaryKeySelective(updateUserCoinBalance);

        }
    }
}

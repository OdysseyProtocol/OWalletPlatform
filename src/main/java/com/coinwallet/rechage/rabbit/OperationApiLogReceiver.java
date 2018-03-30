package com.coinwallet.rechage.rabbit;

import com.alibaba.fastjson.JSON;
import com.coinwallet.rechage.dao.LogOperateApiMapper;
import com.coinwallet.rechage.entity.LogOperateApi;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.Transaction;

/**
 * Created by liuhuan on 2018/3/27.
 */
@Component
public class OperationApiLogReceiver {
    @Autowired
    private LogOperateApiMapper logOperateApiMapper;
    /**
     * @param msg
     */
    @RabbitListener(queues = RabbitRechargeConfig.OPERATION_API_LOG)
    public void recordApiLog(String msg) {
            LogOperateApi logOperateApi = JSON.parseObject(msg, LogOperateApi.class);
            logOperateApiMapper.insertSelective(logOperateApi);

    }

}

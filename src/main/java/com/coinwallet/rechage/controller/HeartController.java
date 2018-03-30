package com.coinwallet.rechage.controller;

import com.coinwallet.common.response.FailResponse;
import com.coinwallet.common.response.ResponseValue;
import com.coinwallet.rechage.controller.req.CreateWalletReq;
import com.coinwallet.rechage.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class HeartController {


    protected org.slf4j.Logger logger = LoggerFactory.getLogger(HeartController.class);
    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public @ResponseBody

    String isAlive( ) {
        logger.warn("===================server started========");
        return  "ok";
    }




}

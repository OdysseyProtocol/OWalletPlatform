package com.coinwallet.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by tlw on 2018/1/23.
 */
public class Constants {


   // public static final String BLOCK_NODE_URL = "http://h.stormfives.com:34378";

    public static final String BLOCK_NODE_URL = "http://172.31.1.75:34378";

    public static final int USER_COIN_RECHARGE = 1;
    public static final int USER_COIN_COLLECT = 2;
    public static final int USER_COIN_CONSUME = 3;
    public static final int USER_COIN_OBTAIN = 4;

    public static final int ORDER_STATUS_PEEDING = 1;
    public static final int ORDER_STATUS_SUCCESS = 2;
    public static final int ORDER_STATUS_FAILED = 3;

    public static final int ORDER_CALLBACK_PROCESS = 1;
    public static final int ORDER_CALLBACK_SUCCESS = 2;


    public static final String USER_PRIVATEKEY_ERROR = "1";
    public static final String USER_LACK_OF_BALANCE = "2";
    public static final String TRANSFER_ERROR = "error";


    public static final int USER_TRANS_NORMAL = 0;
    public static final int USER_IN_TRANS = 1;

    public static final int USER_GAS_ACQUIRE = 2;

    public static BigDecimal COIN_TO_GATHER = new BigDecimal("0");


    public static final int ORDER_TYPE_USER_RECHARGE = 1;
    public static final int ORDER_TYPE_GAS_RECHARGE = 2;
    public static final int ORDER_TYPE_GATHER_RECHARGE = 3;
    public static final int ORDER_TYPE_PLATFORM_TRANSFER = 4;

    public static final int NODE_UNCONFIRM_ROW = 30;
    public static final int SCAN_UNCONFIRM_ROW = 10;

    public static final int NO_TRANSFER_ORDER = 0;
    public static final int IN_TRANSFER_ORDER = 1;


    public static final String GAS_COIN_NAME = "ETH";
    public static final int GAS_COIN_ID = 3;

    public static final int NOT_REPEATED_CALL_BACK_CODE = 400;
    public static final BigDecimal USER_ENOUGH_GAS = new BigDecimal("0.00001");


    //Integers
    public static final Integer ZERO_INTEGER = new Integer("0");
    public static final Integer ONE_INTEGER = new Integer("1");
    public static final Integer TWO_INTEGER = new Integer("2");
    public static final Integer TRHEE_INTEGER = new Integer("3");

    //Byte
    public static final Byte FIVE_BTYE = new Byte("5");
    public static final Byte FOUR_BTYE = new Byte("4");
    public static final Byte THREE_BYTE = new Byte("3");
    public static final Byte TWO_BYTE = new Byte("2");
    public static final Byte ONE_BYTE = new Byte("1");
    public static final Byte ZERO_BYTE = new Byte("0");
    public static final Byte NEGATIVE_ONE_BYTE = new Byte("-1");
}

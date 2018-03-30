package com.coinwallet.common.exception;


import com.coinwallet.common.web.ObikeHttpStatus;
import com.coinwallet.common.web.ObikeResponseStatus;
/**
 *
 * 短信发送频率异常类,表示短信发送频率异常
 * 错误码400
 *
 * Created with IntelliJ IDEA.
 * User: LuoYuanchun
 * Date: 17/12/12
 * Time: 下午9:29
 *
 */
@ObikeResponseStatus(ObikeHttpStatus.BAD_REQUEST)
public class MessageSendRateException extends Exception{

    public MessageSendRateException() {
    }

    public MessageSendRateException(String s) {
        super(s);
    }
}

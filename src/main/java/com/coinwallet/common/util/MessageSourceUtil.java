package com.coinwallet.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

@Component
public class MessageSourceUtil {

    @Resource
    private MessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger(MessageSourceUtil.class);

    public String getMessage(String key) {
        return getMessage(key, null);
    }


    public String getMessage(String key, Object[] args){
        return getMessage(key, args, "");
    }


    public String getMessage(String key,Object[] args,String defaultMessage){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, defaultMessage,locale);
    }
}
package com.coinwallet.common.web;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxb on 18/04/2017.
 */
@Aspect
@Component
@Profile("!default")
public class WebLogAspect {

    @Autowired
    HttpServletRequest request;

    @Pointcut("execution(public * com.coinwallet..controller..*.*(..))")
    public void webLog(){}


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (!request.getMethod().equalsIgnoreCase("get")){
            List<Object> param = new ArrayList<>();
            for (Object object : args){
                if (!(object instanceof HttpServletRequest) && !(object instanceof HttpServletResponse)){
                    param.add(object);
                }
            }
            request.setAttribute("postBody", JSON.toJSONString(param));
        }
    }

}

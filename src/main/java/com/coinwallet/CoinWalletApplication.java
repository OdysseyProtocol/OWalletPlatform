package com.coinwallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.coinwallet.*")
@MapperScan("com.coinwallet.**.**.dao")
public class CoinWalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoinWalletApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

}

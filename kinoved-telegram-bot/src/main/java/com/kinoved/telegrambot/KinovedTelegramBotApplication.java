package com.kinoved.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.kinoved.telegrambot.client")
@SpringBootApplication
public class KinovedTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(KinovedTelegramBotApplication.class, args);
//        var ctx = SpringApplication.run(KinovedTelegramBotApplication.class, args);
//        var bot = ctx.getBean(KinovedTelegramBot.class);
//        bot.getDb().clear();
    }

}

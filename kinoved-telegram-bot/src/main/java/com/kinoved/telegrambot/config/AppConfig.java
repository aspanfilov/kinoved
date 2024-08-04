package com.kinoved.telegrambot.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kinoved.telegrambot.bot.KinovedTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@EnableConfigurationProperties({TelegramBotProps.class, AppProps.class})
public class AppConfig {

    @Autowired
    private TelegramBotProps props;

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(props.getToken());
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsApi(KinovedTelegramBot bot) throws TelegramApiException {
        var botsApi = new TelegramBotsLongPollingApplication();
        botsApi.registerBot(props.getToken(), bot);
        bot.onRegister();
        return botsApi;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}

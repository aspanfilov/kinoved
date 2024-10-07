package com.kinoved.telegrambot.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram.bot")
@Getter
@RequiredArgsConstructor
public class TelegramBotProps {
    private final String username;

    private final String token;

    private final Long creatorId;
}

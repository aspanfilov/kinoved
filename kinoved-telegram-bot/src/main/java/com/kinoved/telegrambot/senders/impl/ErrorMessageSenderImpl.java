package com.kinoved.telegrambot.senders.impl;

import com.kinoved.telegrambot.config.TelegramBotProps;
import com.kinoved.telegrambot.senders.ErrorMessageSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class ErrorMessageSenderImpl implements ErrorMessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorMessageSenderImpl.class);

    private final TelegramBotProps telegramBotProps;

    private final TelegramClient telegramClient;

    @Override
    public void send(Exception ex) {

        String errorMessage = String.format("Произошла ошибка: %s", ex.getMessage());

        SendMessage message = SendMessage.builder()
                .chatId(telegramBotProps.getCreatorId())
                .text(errorMessage)
                .build();

        try {
            telegramClient.execute(message);
            LOG.info("Exception send to telegram: {}", ex.getMessage());
        } catch (TelegramApiException e) {
            LOG.error("Failed to send error message to Telegram", e);
            e.printStackTrace();
        }
    }
}

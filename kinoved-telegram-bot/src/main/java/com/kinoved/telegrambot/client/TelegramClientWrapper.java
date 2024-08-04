package com.kinoved.telegrambot.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@RequiredArgsConstructor
public class TelegramClientWrapper {
    private final TelegramClient telegramClient;

    public void execute(SendMessage method) {
        try {
            telegramClient.execute(method);
        } catch (TelegramApiException e) {
            //todo обработать exception
            e.printStackTrace();
        }
    }

    public void execute(SendPhoto method) {
        try {
            telegramClient.execute(method);
        } catch (TelegramApiException e) {
            //todo обработать exception
            e.printStackTrace();
        }
    }

    public void execute(EditMessageCaption method) {
        try {
            telegramClient.execute(method);
        } catch (TelegramApiException e) {
            //todo обработать exception
            e.printStackTrace();
        }
    }

    public void execute(EditMessageReplyMarkup method) {
        try {
            telegramClient.execute(method);
        } catch (TelegramApiException e) {
            //todo обработать exception
            e.printStackTrace();
        }
    }
}

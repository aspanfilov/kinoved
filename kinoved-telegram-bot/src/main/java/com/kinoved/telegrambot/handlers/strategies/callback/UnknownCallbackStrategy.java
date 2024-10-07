package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.telegrambot.client.TelegramClientWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@RequiredArgsConstructor
public class UnknownCallbackStrategy implements CallbackStrategy {

    private final TelegramClientWrapper telegramClient;

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        SendMessage sendMethod = SendMessage.builder()
                .chatId(chatId)
                .text("Ошибка.Отсутствует обработчик на этот callback")
                .build();
        telegramClient.execute(sendMethod);
    }
}

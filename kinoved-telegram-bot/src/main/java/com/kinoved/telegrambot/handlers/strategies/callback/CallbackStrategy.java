package com.kinoved.telegrambot.handlers.strategies.callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackStrategy {
    void handleCallback(Long chatId, CallbackQuery callbackQuery);
}

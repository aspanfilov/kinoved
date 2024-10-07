package com.kinoved.telegrambot.handlers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackHandler {

    void replyToCallback(Long chatId, CallbackQuery callbackQuery);

}

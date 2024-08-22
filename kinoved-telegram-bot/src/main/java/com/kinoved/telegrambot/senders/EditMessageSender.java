package com.kinoved.telegrambot.senders;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface EditMessageSender {

    void sendDeleteKeyboard(Long chatId, Integer msgId);

    void sendTextAndKeyboard(Long chatId, Integer msgId, String messageText, InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendCaptionAndKeyboard(Long chatId, Integer msgId, String caption, InlineKeyboardMarkup inlineKeyboardMarkup);
}

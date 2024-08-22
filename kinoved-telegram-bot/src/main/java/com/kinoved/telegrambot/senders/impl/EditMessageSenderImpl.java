package com.kinoved.telegrambot.senders.impl;

import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.senders.EditMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
@RequiredArgsConstructor
public class EditMessageSenderImpl implements EditMessageSender {

    private final TelegramClientWrapper telegramClient;

    @Override
    public void sendDeleteKeyboard(Long chatId, Integer msgId) {
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(msgId)
                .replyMarkup(null)
                .build();
        telegramClient.execute(editMessageReplyMarkup);
    }

    @Override
    public void sendTextAndKeyboard(Long chatId, Integer msgId,
                                    String messageText,
                                    InlineKeyboardMarkup inlineKeyboardMarkup) {
        var editMethod = EditMessageText.builder()
                .chatId(chatId)
                .messageId(msgId)
                .text(messageText)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        telegramClient.execute(editMethod);
    }

    @Override
    public void sendCaptionAndKeyboard(Long chatId, Integer msgId,
                                    String caption,
                                    InlineKeyboardMarkup inlineKeyboardMarkup) {
        var editMethod = EditMessageCaption.builder()
                .chatId(chatId)
                .messageId(msgId)
                .caption(caption)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        telegramClient.execute(editMethod);
    }

}

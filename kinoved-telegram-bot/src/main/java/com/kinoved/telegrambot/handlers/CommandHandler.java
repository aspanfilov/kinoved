package com.kinoved.telegrambot.handlers;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface CommandHandler {
    void replyToCommand(Long chatId, Message message);
}

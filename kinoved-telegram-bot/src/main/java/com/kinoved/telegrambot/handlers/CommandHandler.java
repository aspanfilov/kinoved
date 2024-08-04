package com.kinoved.telegrambot.handlers;

public interface CommandHandler {
    void replyToCommand(Long chatId, String command);
}

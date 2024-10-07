package com.kinoved.telegrambot.handlers;

public interface GenreHandler {
    void replyToCommand(Long chatId, Long userId, String genre);
}

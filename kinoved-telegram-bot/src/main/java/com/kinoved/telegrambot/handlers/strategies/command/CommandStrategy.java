package com.kinoved.telegrambot.handlers.strategies.command;

public interface CommandStrategy {
    void handleCommand(Long chatId, Long userId);
}

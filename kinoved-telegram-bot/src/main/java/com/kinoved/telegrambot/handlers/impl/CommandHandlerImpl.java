package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.telegrambot.handlers.CommandHandler;
import com.kinoved.telegrambot.handlers.strategies.command.CommandStrategy;
import com.kinoved.telegrambot.handlers.strategies.command.UnknownCommandStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandHandlerImpl implements CommandHandler {

    private final Map<String, CommandStrategy> strategies;

    private final UnknownCommandStrategy unknownCommandStrategy;

    @Override
    public void replyToCommand(Long chatId, Message message) {
        String command = message.getText();
        CommandStrategy strategy = strategies.getOrDefault(command, unknownCommandStrategy);
        strategy.handleCommand(chatId, message.getFrom().getId());
    }
}

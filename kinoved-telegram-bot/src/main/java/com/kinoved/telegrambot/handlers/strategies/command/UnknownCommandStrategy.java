package com.kinoved.telegrambot.handlers.strategies.command;

import com.kinoved.telegrambot.senders.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnknownCommandStrategy implements CommandStrategy {

    private final MessageSender messageSender;

    @Override
    public void handleCommand(Long chatId, Long userId) {
        messageSender.sendMessage(chatId, "Неизвестная команда");
    }
}

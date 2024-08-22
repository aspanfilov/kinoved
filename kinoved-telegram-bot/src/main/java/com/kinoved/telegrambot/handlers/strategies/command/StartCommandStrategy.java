package com.kinoved.telegrambot.handlers.strategies.command;

import com.kinoved.telegrambot.senders.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.kinoved.telegrambot.constants.Constants.COMMAND_START;
import static com.kinoved.telegrambot.constants.Constants.START_TEXT;

@Component(COMMAND_START)
@RequiredArgsConstructor
public class StartCommandStrategy implements CommandStrategy {

    private final MessageSender messageSender;

    @Override
    public void handleCommand(Long chatId, Long userId) {
        messageSender.sendMessage(chatId, START_TEXT);
    }
}

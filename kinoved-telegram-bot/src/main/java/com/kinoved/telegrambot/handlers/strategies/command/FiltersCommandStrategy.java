package com.kinoved.telegrambot.handlers.strategies.command;

import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.senders.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.kinoved.telegrambot.constants.Constants.COMMAND_FILTERS;

@Component(COMMAND_FILTERS)
@RequiredArgsConstructor
public class FiltersCommandStrategy implements CommandStrategy {

    private final MessageSender messageSender;

    private final KeyboardFactory keyboardFactory;

    @Override
    public void handleCommand(Long chatId, Long userId) {
        messageSender.sendMessageWithInlineKeyboard(chatId,
                "Выберите фильтр:",
                keyboardFactory.getFiltersKeyboard());
    }
}

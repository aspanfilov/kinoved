package com.kinoved.telegrambot.handlers.strategies.command;

import com.kinoved.common.telegram.dtos.SortPreferenceDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.senders.MessageSender;
import com.kinoved.telegrambot.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.kinoved.telegrambot.constants.Constants.COMMAND_SORT;

@Component(COMMAND_SORT)
@RequiredArgsConstructor
public class SortCommandStrategy implements CommandStrategy {

    private final KinovedCoreClient kinovedCoreClient;

    private final MessageSender messageSender;

    private final MessageBuilder messageBuilder;

    private final KeyboardFactory keyboardFactory;

    @Override
    public void handleCommand(Long chatId, Long userId) {
        SortPreferenceDto sortPreferenceDto = kinovedCoreClient.getSortPreference(userId);

        messageSender.sendMessageWithInlineKeyboard(chatId,
                messageBuilder.getSortRequestMsg(sortPreferenceDto),
                keyboardFactory.getSortKeyboard());
    }
}

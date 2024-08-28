package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.common.enums.SortField;
import com.kinoved.common.enums.SortOrder;
import com.kinoved.common.telegram.dtos.SortPreferenceDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.senders.EditMessageSender;
import com.kinoved.telegrambot.utils.MessageBuilder;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class SortMovieCallbackStrategy implements CallbackStrategy {

    private final KinovedCoreClient kinovedCoreClient;
    private final MessageBuilder messageBuilder;
    private final EditMessageSender editMessageSender;
    private final SortField sortField;
    private final SortOrder sortOrder;

    public SortMovieCallbackStrategy(
            KinovedCoreClient kinovedCoreClient,
            MessageBuilder messageBuilder,
            EditMessageSender editMessageSender,
            SortField sortField,
            SortOrder sortOrder) {
        this.kinovedCoreClient = kinovedCoreClient;
        this.messageBuilder = messageBuilder;
        this.editMessageSender = editMessageSender;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        var msgId = callbackQuery.getMessage().getMessageId();
        var msgText = messageBuilder.getSortSettingMsg(sortField, sortOrder);

        var sortPreferenceDto = new SortPreferenceDto(sortField, sortOrder);
        kinovedCoreClient.setSortPreference(callbackQuery.getFrom().getId(), sortPreferenceDto);

        editMessageSender.sendTextAndKeyboard(chatId, msgId, msgText, null);
    }
}

package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.common.telegram.dtos.MovieIdConfirmResponseDto;
import com.kinoved.common.telegram.enums.ConfirmationStatus;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.senders.EditMessageSender;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class MovieIdQueryCallbackStrategy implements CallbackStrategy {

    private final KinovedCoreClient kinovedCoreClient;

    private final CallbackDataUtil callbackDataUtil;

    private final EditMessageSender editMessageSender;

    private final ConfirmationStatus status;

    public MovieIdQueryCallbackStrategy(KinovedCoreClient kinovedCoreClient,
                                        CallbackDataUtil callbackDataUtil,
                                        EditMessageSender editMessageSender,
                                        ConfirmationStatus status) {
        this.kinovedCoreClient = kinovedCoreClient;
        this.callbackDataUtil = callbackDataUtil;
        this.editMessageSender = editMessageSender;
        this.status = status;
    }

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        String movieId = callbackDataUtil.getParamFromCallbackData(callbackQuery.getData(), 1);
        String movieFileInfoId = callbackDataUtil.getParamFromCallbackData(callbackQuery.getData(), 2);
        Integer movieConfirmationMessageId = callbackQuery.getMessage().getMessageId();

        var confirmMovieIdResponseDto = MovieIdConfirmResponseDto.builder()
                .movieKpDevId(Long.parseLong(movieId))
                .movieFileInfoId(movieFileInfoId)
                .confirmationStatus(status)
                .messageId(callbackQuery.getMessage().getMessageId())
                .build();

        kinovedCoreClient.handleMovieIdConfirmation(confirmMovieIdResponseDto);

        editMessageSender.sendDeleteKeyboard(chatId, movieConfirmationMessageId);
    }
}

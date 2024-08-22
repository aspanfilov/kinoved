package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.common.telegram.dtos.MovieIdConfirmResponseDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.senders.EditMessageSender;
import com.kinoved.telegrambot.senders.MessageSender;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.kinoved.common.telegram.enums.ConfirmationStatus.APPROVE;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_CONFIRM;

@Component(CALLBACK_MOVIE_ID_CONFIRM)
@RequiredArgsConstructor
public class MovieIdConfirmCallbackStrategy implements CallbackStrategy {

    private final MessageSender messageSender;

    private final EditMessageSender editMessageSender;

    private final KinovedCoreClient kinovedCoreClient;

    private final CallbackDataUtil callbackDataUtil;

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        String movieId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 1, String.class);
        String movieFileInfoId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 2, String.class);
        Integer movieConfirmationMessageId = callbackQuery.getMessage().getMessageId();

        var confirmMovieIdResponseDto = MovieIdConfirmResponseDto.builder()
                .movieKpDevId(Long.parseLong(movieId))
                .movieFileInfoId(movieFileInfoId)
                .confirmationStatus(APPROVE)
                .build();

        ResponseEntity<Void> response = kinovedCoreClient.handleMovieIdConfirmation(confirmMovieIdResponseDto);
        messageSender.notifyResponseStatus(chatId, movieConfirmationMessageId, response,
                "Описание фильма сохранено.\nФильм переносится в папку жанра...");

        editMessageSender.sendDeleteKeyboard(chatId, movieConfirmationMessageId);
    }
}

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

import static com.kinoved.common.telegram.enums.ConfirmationStatus.REJECTED;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_REJECT;

@Component(CALLBACK_MOVIE_ID_REJECT)
@RequiredArgsConstructor
public class MovieIdRejectCallbackStrategy implements CallbackStrategy {

    private final KinovedCoreClient kinovedCoreClient;

    private final CallbackDataUtil callbackDataUtil;

    private final MessageSender messageSender;

    private final EditMessageSender editMessageSender;

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        String movieId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 1, String.class);
        String movieFileInfoId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 2, String.class);
        Integer movieConfirmationMessageId = callbackQuery.getMessage().getMessageId();

        var confirmMovieIdResponseDto = MovieIdConfirmResponseDto.builder()
                .movieKpDevId(Long.parseLong(movieId))
                .movieFileInfoId(movieFileInfoId)
                .confirmationStatus(REJECTED)
                .build();

        ResponseEntity<Void> response = kinovedCoreClient.handleMovieIdConfirmation(confirmMovieIdResponseDto);
        messageSender.notifyResponseStatus(chatId, movieConfirmationMessageId, response,
                "Данные о файле помечены в статус REJECTED\nФайл ожидает ручной обработки");

        editMessageSender.sendDeleteKeyboard(chatId, movieConfirmationMessageId);
    }
}

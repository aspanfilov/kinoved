package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.common.telegram.dtos.MovieIdConfirmResponseDto;
import com.kinoved.common.telegram.enums.ConfirmationStatus;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.converters.MovieDataConverter;
import com.kinoved.telegrambot.dtos.MovieDto;
import com.kinoved.telegrambot.handlers.CallbackHandler;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.kinoved.common.telegram.enums.ConfirmationStatus.APPROVE;
import static com.kinoved.common.telegram.enums.ConfirmationStatus.ORIGINAL_QUERY;
import static com.kinoved.common.telegram.enums.ConfirmationStatus.REJECTED;
import static com.kinoved.common.telegram.enums.ConfirmationStatus.TRANSLIT_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_CONFIRM;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_ORIGINAL_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_REJECT;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_TRANSLIT_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SHOW_LESS;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SHOW_MORE;

@Component
@RequiredArgsConstructor
public class CallbackHandlerImpl implements CallbackHandler {

    private final TelegramClientWrapper telegramClient;

    private final KinovedCoreClient kinovedCoreClient;

    private final MovieDataConverter movieConverter;

    private final KeyboardFactory keyboardFactory;

    private final CallbackDataUtil callbackDataUtil;

    @Override
    public void replyToCallback(Long chatId, CallbackQuery callbackQuery) {
        String callbackKeyword = callbackDataUtil.getKeywordFromCallbackData(callbackQuery.getData());

        //todo
        switch (callbackKeyword) {
            case CALLBACK_SHOW_MORE -> replyToShowMoreCallback(chatId, callbackQuery);
            case CALLBACK_SHOW_LESS -> replyToShowLessCallback(chatId, callbackQuery);
            case CALLBACK_MOVIE_ID_CONFIRM -> replyToMovieIdConfirmCallback(chatId, callbackQuery);
            case CALLBACK_MOVIE_ID_REJECT -> replyToMovieIdRejectCallback(chatId, callbackQuery);
            case CALLBACK_MOVIE_ID_TRANSLIT_QUERY -> replyToMovieIdQueryCallback(chatId, callbackQuery, TRANSLIT_QUERY);
            case CALLBACK_MOVIE_ID_ORIGINAL_QUERY -> replyToMovieIdQueryCallback(chatId, callbackQuery, ORIGINAL_QUERY);
            default -> replyToUnknownCallback(chatId);
        }
    }

    private void replyToShowMoreCallback(long chatId, CallbackQuery callbackQuery) {

        String movieId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 1, String.class);
        var msgId = callbackQuery.getMessage().getMessageId();

        MovieDto movieDto = kinovedCoreClient.getMovieById(movieId);
        String fullOverview = movieConverter.convertToFullOverview(movieDto);

        var editMethod = EditMessageCaption.builder()
                .chatId(chatId)
                .messageId(msgId)
                .caption(fullOverview)
                .replyMarkup(keyboardFactory.getShowLessKeyboard(movieDto.getId()))
                .build();

        telegramClient.execute(editMethod);
    }

    private void replyToShowLessCallback(Long chatId, CallbackQuery callbackQuery) {

        String movieId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 1, String.class);
        var msgId = callbackQuery.getMessage().getMessageId();

        MovieDto movieDto = kinovedCoreClient.getMovieById(movieId);
        String shortOverview = movieConverter.convertToShortOverview(movieDto);

        var editMethod = EditMessageCaption.builder()
                .chatId(chatId)
                .messageId(msgId)
                .caption(shortOverview)
                .replyMarkup(keyboardFactory.getShowMoreKeyboard(movieDto.getId()))
                .build();

        telegramClient.execute(editMethod);
    }

    private void replyToMovieIdConfirmCallback(long chatId, CallbackQuery callbackQuery) {
        String movieId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 1, String.class);
        String movieFileInfoId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 2, String.class);
        Integer movieConfirmationMessageId = callbackQuery.getMessage().getMessageId();

        var confirmMovieIdResponseDto = MovieIdConfirmResponseDto.builder()
                .movieKpDevId(Long.parseLong(movieId))
                .movieFileInfoId(movieFileInfoId)
                .confirmationStatus(APPROVE)
                .build();

        ResponseEntity<Void> response = kinovedCoreClient.handleMovieIdConfirmation(confirmMovieIdResponseDto);
        notifyResponseStatus(chatId, movieConfirmationMessageId, response,
                "Описание фильма сохранено.\nФильм переносится в папку жанра...");

        deleteMessageKeyboard(chatId, movieConfirmationMessageId);
    }

    private void replyToMovieIdRejectCallback(long chatId, CallbackQuery callbackQuery) {
        String movieId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 1, String.class);
        String movieFileInfoId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 2, String.class);
        Integer movieConfirmationMessageId = callbackQuery.getMessage().getMessageId();

        var confirmMovieIdResponseDto = MovieIdConfirmResponseDto.builder()
                .movieKpDevId(Long.parseLong(movieId))
                .movieFileInfoId(movieFileInfoId)
                .confirmationStatus(REJECTED)
                .build();

        ResponseEntity<Void> response = kinovedCoreClient.handleMovieIdConfirmation(confirmMovieIdResponseDto);
        notifyResponseStatus(chatId, movieConfirmationMessageId, response,
                "Данные о файле помечены в статус REJECTED\nФайл ожидает ручной обработки");

        deleteMessageKeyboard(chatId, movieConfirmationMessageId);
    }

    private void replyToMovieIdQueryCallback(long chatId,
                                             CallbackQuery callbackQuery,
                                             ConfirmationStatus status) {

        String movieId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 1, String.class);
        String movieFileInfoId = callbackDataUtil.getObjectFromCallbackData(callbackQuery.getData(), 2, String.class);
        Integer movieConfirmationMessageId = callbackQuery.getMessage().getMessageId();

        var confirmMovieIdResponseDto = MovieIdConfirmResponseDto.builder()
                .movieKpDevId(Long.parseLong(movieId))
                .movieFileInfoId(movieFileInfoId)
                .confirmationStatus(status)
                .messageId(callbackQuery.getMessage().getMessageId())
                .build();

        kinovedCoreClient.handleMovieIdConfirmation(confirmMovieIdResponseDto);

        deleteMessageKeyboard(chatId, movieConfirmationMessageId);
    }

    private void replyToUnknownCallback(long chatId) {
        SendMessage sendMethod = SendMessage.builder()
                .chatId(chatId)
                .text("Ошибка.Отсутствует обработчик на этот callback")
                .build();
        telegramClient.execute(sendMethod);
    }

    private void notifyResponseStatus(Long chatId, Integer msgId, ResponseEntity<Void> response, String successMessage) {
        if (response.getStatusCode().is2xxSuccessful()) {
            telegramClient.execute(SendMessage.builder()
                    .chatId(chatId)
                    .replyToMessageId(msgId)
                    .text(successMessage)
                    .build());
            return;
        }
        telegramClient.execute(SendMessage.builder()
                .chatId(chatId)
                .replyToMessageId(msgId)
                .text(String.format("Произошла ошибка: %s", response.getStatusCode()))
                .build());
    }

    private void deleteMessageKeyboard(Long chatId, Integer msgId) {
        telegramClient.execute(EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(msgId)
                .replyMarkup(null)
                .build());
    }
}

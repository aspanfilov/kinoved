package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.common.telegram.dtos.MovieDto;
import com.kinoved.common.telegram.dtos.UpdateMovieDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.converters.MovieDataMapper;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.senders.EditMessageSender;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MARK_WATCHED_MOVIE;

@Component(CALLBACK_MARK_WATCHED_MOVIE)
@RequiredArgsConstructor
public class MarkWatchedMovieCallbackStrategy implements CallbackStrategy {

    private final CallbackDataUtil callbackDataUtil;

    private final KinovedCoreClient kinovedCoreClient;

    private final MovieDataMapper movieMapper;

    private final EditMessageSender editMessageSender;

    private final KeyboardFactory keyboardFactory;

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        var movieId = callbackDataUtil.getParamFromCallbackData(callbackQuery.getData(), 1);
        var isWatchedByString = callbackDataUtil.getParamFromCallbackData(callbackQuery.getData(), 2);
        var isWatched = Boolean.parseBoolean(isWatchedByString);
        var msgId = callbackQuery.getMessage().getMessageId();

        var updateMovieDto = UpdateMovieDto.builder()
                .watched(!isWatched)
                .build();

        MovieDto updatedMovieDto = kinovedCoreClient.updateMovie(movieId, updateMovieDto);
        var shortOverview = movieMapper.convertToShortOverview(updatedMovieDto);

        editMessageSender.sendCaptionAndKeyboard(chatId, msgId,
                shortOverview,
                keyboardFactory.getShowMoreKeyboard(
                        updatedMovieDto.getId(),
                        updatedMovieDto.getExternalId().getKpDev(),
                        updatedMovieDto.getFavorite(),
                        updatedMovieDto.getWatched()));
    }
}

package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.converters.MovieDataMapper;
import com.kinoved.common.telegram.dtos.MovieDto;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.senders.EditMessageSender;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SHOW_LESS_MOVIE_INFO;

@Component(CALLBACK_SHOW_LESS_MOVIE_INFO)
@RequiredArgsConstructor
public class ShowLessMovieInfoCallbackStrategy implements CallbackStrategy {

    private final KinovedCoreClient kinovedCoreClient;

    private final MovieDataMapper movieMapper;

    private final KeyboardFactory keyboardFactory;

    private final CallbackDataUtil callbackDataUtil;

    private final EditMessageSender editMessageSender;

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        String movieId = callbackDataUtil.getParamFromCallbackData(callbackQuery.getData(), 1);
        var msgId = callbackQuery.getMessage().getMessageId();

        MovieDto movieDto = kinovedCoreClient.getMovieById(movieId);
        String shortOverview = movieMapper.convertToShortOverview(movieDto);

        editMessageSender.sendCaptionAndKeyboard(chatId, msgId,
                shortOverview,
                keyboardFactory.getShowMoreKeyboard(
                        movieDto.getId(),
                        movieDto.getExternalId().getKpDev(),
                        movieDto.getFavorite(),
                        movieDto.getWatched()));

    }
}

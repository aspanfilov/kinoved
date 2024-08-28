package com.kinoved.telegrambot.handlers.strategies.callback;

import com.kinoved.common.telegram.dtos.MovieDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.senders.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Collections;
import java.util.List;

import static com.kinoved.telegrambot.constants.Constants.CALLBACK_FILTER_LAST_MOVIE;

@Component(CALLBACK_FILTER_LAST_MOVIE)
@RequiredArgsConstructor
public class FilterLastMovieCallbackStrategy implements CallbackStrategy {

    private final KinovedCoreClient kinovedCoreClient;

    private final MessageSender messageSender;

    @Override
    public void handleCallback(Long chatId, CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        List<MovieDto> movieDtos = kinovedCoreClient.getMovies(userId, Collections.emptyList(),
                true, null, null);
        movieDtos.forEach(movieDto -> messageSender.sendMovieOverview(chatId, movieDto));
    }
}

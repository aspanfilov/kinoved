package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.common.telegram.dtos.MovieDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.handlers.GenreHandler;
import com.kinoved.telegrambot.senders.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreHandlerImpl implements GenreHandler {

    private final KinovedCoreClient kinovedCoreClient;

    private final MessageSender messageSender;

    @Override
    public void replyToCommand(Long chatId, Long userId, String genre) {
        List<MovieDto> movieDtos = kinovedCoreClient.getMovies(userId, List.of(genre),
                null, null, null);
        movieDtos.forEach(movieDto -> messageSender.sendMovieOverview(chatId, movieDto));
    }
}

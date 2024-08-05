package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.dtos.MovieDto;
import com.kinoved.telegrambot.handlers.GenreHandler;
import com.kinoved.telegrambot.handlers.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreHandlerImpl implements GenreHandler {

    private final KinovedCoreClient kinovedCoreClient;

    private final MessageSender messageSender;

    @Override
    public void replyToCommand(Long chatId, String genre) {
        List<MovieDto> movieDtos = kinovedCoreClient.getAllMovies(genre);
        movieDtos.forEach(movieDto -> messageSender.sendMovieOverview(chatId, movieDto));
    }
}

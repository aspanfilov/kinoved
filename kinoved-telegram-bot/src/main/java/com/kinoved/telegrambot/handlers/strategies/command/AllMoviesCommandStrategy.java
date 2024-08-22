package com.kinoved.telegrambot.handlers.strategies.command;

import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.dtos.MovieDto;
import com.kinoved.telegrambot.senders.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kinoved.telegrambot.constants.Constants.COMMAND_ALL_MOVIES;

@Component(COMMAND_ALL_MOVIES)
@RequiredArgsConstructor
public class AllMoviesCommandStrategy implements CommandStrategy{

    private final KinovedCoreClient kinovedCoreClient;

    private final MessageSender messageSender;

    @Override
    public void handleCommand(Long chatId, Long userId) {
        List<MovieDto> movieDtos = kinovedCoreClient.getMovies(userId, null);
        movieDtos.forEach(movieDto -> messageSender.sendMovieOverview(chatId, movieDto));
    }
}

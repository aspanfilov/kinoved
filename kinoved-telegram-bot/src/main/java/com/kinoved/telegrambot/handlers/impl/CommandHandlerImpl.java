package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.converters.MovieDataConverter;
import com.kinoved.telegrambot.dtos.MovieDto;
import com.kinoved.telegrambot.handlers.CommandHandler;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.List;

import static com.kinoved.telegrambot.constants.Constants.COMMAND_ALL;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_FIRST;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_START;
import static com.kinoved.telegrambot.constants.Constants.START_TEXT;

@Component
@RequiredArgsConstructor
public class CommandHandlerImpl implements CommandHandler {

    private final TelegramClientWrapper telegramClient;

    private final KinovedCoreClient kinovedCoreClient;

    private final MovieDataConverter movieConverter;

    private final KeyboardFactory keyboardFactory;

    @Override
    public void replyToCommand(Long chatId, String command) {
        switch (command) {
            case COMMAND_START -> replyToStartCommand(chatId);
            case COMMAND_FIRST -> replyToFirstMovieCommand(chatId);
            case COMMAND_ALL -> replyToAllMoviesCommand(chatId);
            default -> replyToUnknownCommand(chatId);
        }
    }

    private void replyToStartCommand(long chatId) {
        SendMessage sendMethod = SendMessage.builder()
                .chatId(chatId)
                .text(START_TEXT)
                .build();
        telegramClient.execute(sendMethod);
    }

    private void replyToUnknownCommand(long chatId) {
        SendMessage sendMethod = SendMessage.builder()
                .chatId(chatId)
                .text("Неизвестная команда")
                .build();
        telegramClient.execute(sendMethod);
    }

    private void replyToFirstMovieCommand(long chatId) {
        MovieDto movieDto = kinovedCoreClient.getFirstMovie();
        sendMovieOverview(chatId, movieDto);
    }

    private void replyToAllMoviesCommand(long chatId) {
        List<MovieDto> movieDtos = kinovedCoreClient.getAllMovies();
        movieDtos.forEach(movieDto -> sendMovieOverview(chatId, movieDto));
    }

    private void sendMovieOverview(long chatId, MovieDto movieDto) {
        String shortOverview = movieConverter.convertToShortOverview(movieDto);
        var sendMethod = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(movieDto.getPoster().getPreviewUrl()))
                .caption(shortOverview)
                .replyMarkup(keyboardFactory.getShowMoreKeyboard(movieDto.getId()))
                .build();
        telegramClient.execute(sendMethod);
    }

}

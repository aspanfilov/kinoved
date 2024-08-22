package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.common.enums.MovieFileStatus;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.telegram.dtos.SortPreferenceDto;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.dtos.MovieDto;
import com.kinoved.telegrambot.handlers.CommandHandler;
import com.kinoved.telegrambot.senders.MessageSender;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

import static com.kinoved.telegrambot.constants.Constants.CHOOSE_GENRE_TEXT;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_ALL_MOVIES;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_GENRES;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_SORT;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_START;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_UNFINISHED_FILES;
import static com.kinoved.telegrambot.constants.Constants.NO_GENRE_TEXT;
import static com.kinoved.telegrambot.constants.Constants.START_TEXT;

//@Component
//@RequiredArgsConstructor
public class CommandHandlerImpl2 { //implements CommandHandler {

//    private final TelegramClientWrapper telegramClient;
//
//    private final KinovedCoreClient kinovedCoreClient;
//
//    private final MessageSender messageSender;
//
//    private final MessageBuilder messageBuilder;
//
//    private final KeyboardFactory keyboardFactory;
//
//    @Override
//    public void replyToCommand(Long chatId, Message message) {
//        switch (message.getText()) {
//            case COMMAND_START -> replyToStartCommand(chatId);
//            case COMMAND_ALL_MOVIES -> replyToAllMoviesCommand(chatId, message.getFrom().getId());
//            case COMMAND_GENRES -> replyToGenresCommand(chatId);
//            case COMMAND_SORT -> replyToSortCommand(chatId, message.getFrom().getId());
//            case COMMAND_UNFINISHED_FILES -> replyToUnfinishedFilesCommand(chatId);
//            default -> replyToUnknownCommand(chatId);
//        }
//    }
//
//    private void replyToStartCommand(long chatId) {
//        SendMessage sendMethod = SendMessage.builder()
//                .chatId(chatId)
//                .text(START_TEXT)
//                .build();
//        telegramClient.execute(sendMethod);
//    }
//
//    private void replyToUnknownCommand(long chatId) {
//        SendMessage sendMethod = SendMessage.builder()
//                .chatId(chatId)
//                .text("Неизвестная команда")
//                .build();
//        telegramClient.execute(sendMethod);
//    }
//
//    private void replyToAllMoviesCommand(long chatId, long userId) {
//        List<MovieDto> movieDtos = kinovedCoreClient.getMovies(userId, null);
//        movieDtos.forEach(movieDto -> messageSender.sendMovieOverview(chatId, movieDto));
//    }
//
//    private void replyToGenresCommand(long chatId) {
//        List<String> genres = kinovedCoreClient.getGenres();
//
//        SendMessage sendMethod;
//        if (genres.isEmpty()) {
//            sendMethod = SendMessage.builder()
//                    .chatId(chatId)
//                    .text(NO_GENRE_TEXT)
//                    .build();
//        } else {
//            sendMethod = SendMessage.builder()
//                    .chatId(chatId)
//                    .text(CHOOSE_GENRE_TEXT)
//                    .replyMarkup(keyboardFactory.getGenresKeyboard(genres))
//                    .build();
//        }
//
//        telegramClient.execute(sendMethod);
//    }
//
//    private void replyToSortCommand(long chatId, long userId) {
//        SortPreferenceDto sortPreferenceDto = kinovedCoreClient.getSortPreference(userId);
//        SendMessage sendMethod = SendMessage.builder()
//                .chatId(chatId)
//                .text(messageBuilder.getSortRequestMsg(sortPreferenceDto))
//                .replyMarkup(keyboardFactory.getSortKeyboard())
//                .build();
//        telegramClient.execute(sendMethod);
//    }
//
//    private void replyToUnfinishedFilesCommand(long chatId) {
//
//        List<MovieFileStatus> unfinishedStatuses = List.of(
//                MovieFileStatus.NEW,
//                MovieFileStatus.MATCHED,
//                MovieFileStatus.REJECTED,
//                MovieFileStatus.MOVE_ERROR);
//
//        List<MovieFileInfoDto> movieFileInfoDtos = kinovedCoreClient.getMovieFilesInfo(unfinishedStatuses);
//
//        SendMessage sendMethod = SendMessage.builder()
//                .chatId(chatId)
//                .text(messageBuilder.getUnfinishedFilesMsg(movieFileInfoDtos))
//                .build();
//        telegramClient.execute(sendMethod);
//    }

}

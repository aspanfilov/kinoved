package com.kinoved.telegrambot.handlers.strategies.command;

import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.senders.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kinoved.telegrambot.constants.Constants.CHOOSE_GENRE_TEXT;
import static com.kinoved.telegrambot.constants.Constants.COMMAND_GENRES;
import static com.kinoved.telegrambot.constants.Constants.NO_GENRE_TEXT;

@Component(COMMAND_GENRES)
@RequiredArgsConstructor
public class GenresCommandStrategy implements CommandStrategy {

    private final KinovedCoreClient kinovedCoreClient;

    private final KeyboardFactory keyboardFactory;

    private final MessageSender messageSender;

    @Override
    public void handleCommand(Long chatId, Long userId) {
        List<String> genres = kinovedCoreClient.getGenres();

        if (genres.isEmpty()) {
            messageSender.sendMessage(chatId, NO_GENRE_TEXT);
            return;
        }

        messageSender.sendMessageWithReplyKeyboard(
                chatId, CHOOSE_GENRE_TEXT,
                keyboardFactory.getGenresKeyboard(genres));
    }
}
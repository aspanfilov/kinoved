package com.kinoved.telegrambot.keyboard;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static com.kinoved.telegrambot.constants.Constants.BUTTON_ORIGINAL_QUERY;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_REJECT;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SHOW_LESS;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SHOW_MORE;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_TRANSLIT_QUERY;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_YES;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_CONFIRM;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_ORIGINAL_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_REJECT;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_TRANSLIT_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SHOW_LESS;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SHOW_MORE;

@Component
@RequiredArgsConstructor
public class KeyboardFactoryImpl implements KeyboardFactory {

    private final CallbackDataUtil callbackDataUtils;

    @Override
    public InlineKeyboardMarkup getShowMoreKeyboard(String movieId) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(BUTTON_SHOW_MORE)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SHOW_MORE, movieId))
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(button))
                .build();
    }

    @Override
    public InlineKeyboardMarkup getShowLessKeyboard(String movieId) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(BUTTON_SHOW_LESS)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SHOW_LESS, movieId))
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(button))
                .build();
    }

    @Override
    public InlineKeyboardMarkup getMovieIdConfirmKeyboard(Long movieId,
                                                          MovieFileInfoDto movieFileInfoDto,
                                                          boolean isOriginalConfirmation) {

        InlineKeyboardButton yesButton = InlineKeyboardButton.builder()
                .text(BUTTON_YES)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MOVIE_ID_CONFIRM,
                        movieId,
                        movieFileInfoDto.getId()))
                .build();

        InlineKeyboardButton rejectButton = InlineKeyboardButton.builder()
                .text(BUTTON_REJECT)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MOVIE_ID_REJECT,
                        movieId,
                        movieFileInfoDto.getId()))
                .build();

        InlineKeyboardButton queryButton;
        if (isOriginalConfirmation) {
            queryButton = InlineKeyboardButton.builder()
                    .text(BUTTON_TRANSLIT_QUERY)
                    .callbackData(callbackDataUtils.createCallbackData(
                            CALLBACK_MOVIE_ID_TRANSLIT_QUERY,
                            movieId,
                            movieFileInfoDto.getId()))
                    .build();
        } else {
            queryButton = InlineKeyboardButton.builder()
                    .text(BUTTON_ORIGINAL_QUERY)
                    .callbackData(callbackDataUtils.createCallbackData(
                            CALLBACK_MOVIE_ID_ORIGINAL_QUERY,
                            movieId,
                            movieFileInfoDto.getId()))
                    .build();
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(yesButton, queryButton, rejectButton))
                .build();
    }

    @Override
    public ReplyKeyboardMarkup getGenresKeyboard(List<String> genres) {

        List<String> genreList = new ArrayList<>(genres);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (int i = 0; i < genreList.size(); i++) {
            if (i % 3 == 0 && !currentRow.isEmpty()) {
                keyboardRows.add(currentRow);
                currentRow = new KeyboardRow();
            }
            currentRow.add(new KeyboardButton(genreList.get(i)));
        }

        if (!currentRow.isEmpty()) {
            keyboardRows.add(currentRow);
        }

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }
}

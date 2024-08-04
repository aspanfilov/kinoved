package com.kinoved.telegrambot.keyboard;

import com.kinoved.telegrambot.util.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import static com.kinoved.telegrambot.constants.Constants.BUTTON_REJECT;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SHOW_LESS;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SHOW_MORE;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_YES;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_CONFIRM;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_REJECT;
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
    public InlineKeyboardMarkup getMovieIdConfirmKeyboard(Long movieId, String movieFileInfoId) {

        InlineKeyboardButton buttonYes = InlineKeyboardButton.builder()
                .text(BUTTON_YES)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MOVIE_ID_CONFIRM,
                        movieId,
                        movieFileInfoId))
                .build();

        InlineKeyboardButton buttonNo = InlineKeyboardButton.builder()
                .text(BUTTON_REJECT)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MOVIE_ID_REJECT,
                        movieId,
                        movieFileInfoId))
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(buttonYes, buttonNo))
                .build();
    }
}

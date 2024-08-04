package com.kinoved.telegrambot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardFactory {

    InlineKeyboardMarkup getShowMoreKeyboard(String movieId);

    InlineKeyboardMarkup getShowLessKeyboard(String movieId);

    InlineKeyboardMarkup getMovieIdConfirmKeyboard(Long movieId, String movieFileInfoId);


}

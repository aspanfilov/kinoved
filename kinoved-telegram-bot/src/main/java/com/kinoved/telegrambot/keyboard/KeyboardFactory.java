package com.kinoved.telegrambot.keyboard;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface KeyboardFactory {

    InlineKeyboardMarkup getShowMoreKeyboard(String movieId, Long kpDevId);

    InlineKeyboardMarkup getShowLessKeyboard(String movieId, Long kpDevId);

    InlineKeyboardMarkup getMovieIdConfirmKeyboard(Long movieId,
                                                   MovieFileInfoDto movieFileInfoDto,
                                                   boolean isOriginalConfirmation);

    ReplyKeyboardMarkup getGenresKeyboard(List<String> genres);

    InlineKeyboardMarkup getSortKeyboard();
}

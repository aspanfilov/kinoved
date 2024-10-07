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

import static com.kinoved.telegrambot.constants.Constants.BUTTON_FAVORITE;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_FILTER_FAVORITE;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_FILTER_HIGH_RES;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_FILTER_LAST;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_FILTER_WATCHED;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_ORIGINAL_QUERY;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_REJECT;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SHOW_LESS;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SHOW_MORE;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SORT_DATE_ASC;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SORT_DATE_DESC;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SORT_RATING_ASC;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_SORT_RATING_DESC;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_STILLS;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_TRANSLIT_QUERY;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_WATCHED;
import static com.kinoved.telegrambot.constants.Constants.BUTTON_YES;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MARK_FAVORITE_MOVIE;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_FILTER_FAVORITE_MOVIE;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_FILTER_HIGH_RES_MOVIE;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_FILTER_LAST_MOVIE;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_FILTER_WATCHED_MOVIE;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_CONFIRM;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_ORIGINAL_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_REJECT;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_TRANSLIT_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SHOW_LESS_MOVIE_INFO;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SHOW_MORE_MOVIE_INFO;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_DATE_ASC;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_DATE_DESC;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_RATING_ASC;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_RATING_DESC;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_STILLS;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MARK_WATCHED_MOVIE;

@Component
@RequiredArgsConstructor
public class KeyboardFactoryImpl implements KeyboardFactory {

    private final CallbackDataUtil callbackDataUtils;

    @Override
    public InlineKeyboardMarkup getShowMoreKeyboard(String movieId, Long kpDevId,
                                                    Boolean isFavorite, Boolean isWatched) {
        InlineKeyboardButton buttonMore = InlineKeyboardButton.builder()
                .text(BUTTON_SHOW_MORE)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SHOW_MORE_MOVIE_INFO, movieId))
                .build();

        InlineKeyboardButton buttonStills = InlineKeyboardButton.builder()
                .text(BUTTON_STILLS)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_STILLS, kpDevId.toString()))
                .build();

        InlineKeyboardButton buttonFavorite = InlineKeyboardButton.builder()
                .text(BUTTON_FAVORITE)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MARK_FAVORITE_MOVIE, kpDevId.toString(), isFavorite.toString()))
                .build();

        InlineKeyboardButton buttonWatched = InlineKeyboardButton.builder()
                .text(BUTTON_WATCHED)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MARK_WATCHED_MOVIE, kpDevId.toString(), isWatched.toString()))
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(buttonStills, buttonFavorite, buttonWatched))
                .keyboardRow(new InlineKeyboardRow(buttonMore))
                .build();
    }

    @Override
    public InlineKeyboardMarkup getShowLessKeyboard(String movieId, Long kpDevId,
                                                    Boolean isFavorite, Boolean isWatched) {
        InlineKeyboardButton buttonLess = InlineKeyboardButton.builder()
                .text(BUTTON_SHOW_LESS)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SHOW_LESS_MOVIE_INFO, movieId))
                .build();

        InlineKeyboardButton buttonStills = InlineKeyboardButton.builder()
                .text(BUTTON_STILLS)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_STILLS, kpDevId.toString()))
                .build();

        InlineKeyboardButton buttonFavorite = InlineKeyboardButton.builder()
                .text(BUTTON_FAVORITE)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MARK_FAVORITE_MOVIE, kpDevId.toString(), isFavorite.toString()))
                .build();

        InlineKeyboardButton buttonWatched = InlineKeyboardButton.builder()
                .text(BUTTON_WATCHED)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MARK_WATCHED_MOVIE, kpDevId.toString(), isWatched.toString()))
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(buttonStills, buttonFavorite, buttonWatched))
                .keyboardRow(new InlineKeyboardRow(buttonLess))
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
                        movieId.toString(),
                        movieFileInfoDto.getId()))
                .build();

        InlineKeyboardButton rejectButton = InlineKeyboardButton.builder()
                .text(BUTTON_REJECT)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_MOVIE_ID_REJECT,
                        movieId.toString(),
                        movieFileInfoDto.getId()))
                .build();

        InlineKeyboardButton queryButton;
        if (isOriginalConfirmation) {
            queryButton = InlineKeyboardButton.builder()
                    .text(BUTTON_TRANSLIT_QUERY)
                    .callbackData(callbackDataUtils.createCallbackData(
                            CALLBACK_MOVIE_ID_TRANSLIT_QUERY,
                            movieId.toString(),
                            movieFileInfoDto.getId()))
                    .build();
        } else {
            queryButton = InlineKeyboardButton.builder()
                    .text(BUTTON_ORIGINAL_QUERY)
                    .callbackData(callbackDataUtils.createCallbackData(
                            CALLBACK_MOVIE_ID_ORIGINAL_QUERY,
                            movieId.toString(),
                            movieFileInfoDto.getId()))
                    .build();
        }

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(yesButton, queryButton, rejectButton))
                .build();
    }

    @Override
    public ReplyKeyboardMarkup getGenresKeyboard(List<String> genres) {

        List<KeyboardRow> keyboardRows = createKeyboardRows(genres);

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();
    }

    @Override
    public InlineKeyboardMarkup getSortKeyboard() {
        InlineKeyboardButton ratingAscSortButton = InlineKeyboardButton.builder()
                .text(BUTTON_SORT_RATING_ASC)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SORT_RATING_ASC))
                .build();

        InlineKeyboardButton ratingDescSortButton = InlineKeyboardButton.builder()
                .text(BUTTON_SORT_RATING_DESC)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SORT_RATING_DESC))
                .build();

        InlineKeyboardButton dateAscSortButton = InlineKeyboardButton.builder()
                .text(BUTTON_SORT_DATE_ASC)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SORT_DATE_ASC))
                .build();

        InlineKeyboardButton dateDescSortButton = InlineKeyboardButton.builder()
                .text(BUTTON_SORT_DATE_DESC)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_SORT_DATE_DESC))
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(
                        ratingAscSortButton,
                        ratingDescSortButton,
                        dateAscSortButton,
                        dateDescSortButton))
                .build();
    }

    @Override
    public InlineKeyboardMarkup getFiltersKeyboard() {
        InlineKeyboardButton lastButton = InlineKeyboardButton.builder()
                .text(BUTTON_FILTER_LAST)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_FILTER_LAST_MOVIE))
                .build();

        InlineKeyboardButton favoriteButton = InlineKeyboardButton.builder()
                .text(BUTTON_FILTER_FAVORITE)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_FILTER_FAVORITE_MOVIE))
                .build();

        InlineKeyboardButton highResolutionButton = InlineKeyboardButton.builder()
                .text(BUTTON_FILTER_HIGH_RES)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_FILTER_HIGH_RES_MOVIE))
                .build();

        InlineKeyboardButton watchedButton = InlineKeyboardButton.builder()
                .text(BUTTON_FILTER_WATCHED)
                .callbackData(callbackDataUtils.createCallbackData(
                        CALLBACK_FILTER_WATCHED_MOVIE))
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(new InlineKeyboardRow(
                        lastButton,
                        favoriteButton,
                        highResolutionButton,
                        watchedButton))
                .build();
    }

    private List<KeyboardRow> createKeyboardRows(List<String> genres) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (int i = 0; i < genres.size(); i++) {
            if (i % 3 == 0 && !currentRow.isEmpty()) {
                keyboardRows.add(currentRow);
                currentRow = new KeyboardRow();
            }

            currentRow.add(new KeyboardButton(genres.get(i)));
        }

        if (!currentRow.isEmpty()) {
            keyboardRows.add(currentRow);
        }

        return keyboardRows;
    }
}

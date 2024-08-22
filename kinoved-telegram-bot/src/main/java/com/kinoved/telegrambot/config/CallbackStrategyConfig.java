package com.kinoved.telegrambot.config;

import com.kinoved.common.enums.SortField;
import com.kinoved.common.enums.SortOrder;
import com.kinoved.common.telegram.enums.ConfirmationStatus;
import com.kinoved.telegrambot.client.KinovedCoreClient;
import com.kinoved.telegrambot.handlers.strategies.callback.MovieSortCallbackStrategy;
import com.kinoved.telegrambot.senders.EditMessageSender;
import com.kinoved.telegrambot.handlers.strategies.callback.MovieIdQueryCallbackStrategy;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import com.kinoved.telegrambot.utils.MessageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_ORIGINAL_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_MOVIE_ID_TRANSLIT_QUERY;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_DATE_ASC;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_DATE_DESC;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_RATING_ASC;
import static com.kinoved.telegrambot.constants.Constants.CALLBACK_SORT_RATING_DESC;

@Configuration
public class CallbackStrategyConfig {

    @Bean(CALLBACK_MOVIE_ID_TRANSLIT_QUERY)
    public MovieIdQueryCallbackStrategy movieIdTranslitQueryCallbackStrategy(
            KinovedCoreClient kinovedCoreClient,
            EditMessageSender editMessageSender,
            CallbackDataUtil callbackDataUtil) {

        return new MovieIdQueryCallbackStrategy(
                kinovedCoreClient,
                callbackDataUtil,
                editMessageSender,
                ConfirmationStatus.TRANSLIT_QUERY);
    }

    @Bean(CALLBACK_MOVIE_ID_ORIGINAL_QUERY)
    public MovieIdQueryCallbackStrategy movieIdOriginalQueryCallbackStrategy(
            KinovedCoreClient kinovedCoreClient,
            EditMessageSender editMessageSender,
            CallbackDataUtil callbackDataUtil) {

        return new MovieIdQueryCallbackStrategy(
                kinovedCoreClient,
                callbackDataUtil,
                editMessageSender,
                ConfirmationStatus.ORIGINAL_QUERY);
    }

    @Bean(CALLBACK_SORT_RATING_ASC)
    public MovieSortCallbackStrategy movieSortRatingAscCallbackStrategy(
            KinovedCoreClient kinovedCoreClient,
            MessageBuilder messageBuilder,
            EditMessageSender editMessageSender) {

        return new MovieSortCallbackStrategy(
                kinovedCoreClient,
                messageBuilder,
                editMessageSender,
                SortField.RATING,
                SortOrder.ASC);
    }

    @Bean(CALLBACK_SORT_RATING_DESC)
    public MovieSortCallbackStrategy movieSortRatingDescCallbackStrategy(
            KinovedCoreClient kinovedCoreClient,
            MessageBuilder messageBuilder,
            EditMessageSender editMessageSender) {

        return new MovieSortCallbackStrategy(
                kinovedCoreClient,
                messageBuilder,
                editMessageSender,
                SortField.RATING,
                SortOrder.DESC);
    }

    @Bean(CALLBACK_SORT_DATE_ASC)
    public MovieSortCallbackStrategy movieSortDateAscCallbackStrategy(
            KinovedCoreClient kinovedCoreClient,
            MessageBuilder messageBuilder,
            EditMessageSender editMessageSender) {

        return new MovieSortCallbackStrategy(
                kinovedCoreClient,
                messageBuilder,
                editMessageSender,
                SortField.DOWNLOAD_DATE,
                SortOrder.ASC);
    }

    @Bean(CALLBACK_SORT_DATE_DESC)
    public MovieSortCallbackStrategy movieSortDateDescCallbackStrategy(
            KinovedCoreClient kinovedCoreClient,
            MessageBuilder messageBuilder,
            EditMessageSender editMessageSender) {

        return new MovieSortCallbackStrategy(
                kinovedCoreClient,
                messageBuilder,
                editMessageSender,
                SortField.DOWNLOAD_DATE,
                SortOrder.DESC);
    }
}

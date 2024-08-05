package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieDtoV14;
import com.kinoved.common.telegram.dtos.MovieIdConfirmRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import com.kinoved.common.telegram.enums.NotificationType;
import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.converters.MovieDataConverter;
import com.kinoved.telegrambot.dtos.MovieDto;
import com.kinoved.telegrambot.handlers.MessageSender;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.enums.Emoji;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderImpl.class);

    private final TelegramClientWrapper telegramClient;

    private final KeyboardFactory keyboardFactory;

    private final MovieDataConverter movieDataConverter;

    private final MovieDataConverter movieConverter;

    @Override
    public void sendMovieIdentificationMessage(Long chatId,
                                               MovieIdConfirmRequestDto movieIdConfirmRequestDto,
                                               boolean isOriginalConfirmation) {
        SearchMovieDtoV14 searchMovieDto = movieIdConfirmRequestDto.getSearchMovieDtos().get(0);

        LOG.info("Sending movie identification message to chatId: {}, searchMovieDto: {}", chatId, searchMovieDto);

        String movieOverview = movieDataConverter.convertToIdentificationOverview(searchMovieDto);
        String identificationMessage = getIdentificationMessage(
                movieIdConfirmRequestDto.getMovieFileInfoDto().getFileName(),
                movieIdConfirmRequestDto.getSearchQuery(),
                movieOverview,
                0,
                movieIdConfirmRequestDto.getSearchMovieDtos().size());

        var sendMethod = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(searchMovieDto.getPoster().getPreviewUrl()))
                .caption(identificationMessage)
                .replyToMessageId(movieIdConfirmRequestDto.getMessageId())
                .replyMarkup(keyboardFactory.getMovieIdConfirmKeyboard(
                        searchMovieDto.getId(),
                        movieIdConfirmRequestDto.getMovieFileInfoDto(),
                        isOriginalConfirmation))
                .build();

        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendNotificationMessage(Long chatId, SimpleNotificationDto simpleNotificationDto) {
        var sendMethod = SendMessage.builder()
                .chatId(chatId)
                .text(getNotificationTypeEmoji(simpleNotificationDto.getNotificationType())
                        + simpleNotificationDto.getMessage())
                .replyToMessageId(simpleNotificationDto.getReplyToMessageId())
                .build();
        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendMovieOverview(long chatId, MovieDto movieDto) {
        String shortOverview = movieConverter.convertToShortOverview(movieDto);
        var sendMethod = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(movieDto.getPoster().getPreviewUrl()))
                .caption(shortOverview)
                .replyMarkup(keyboardFactory.getShowMoreKeyboard(movieDto.getId()))
                .build();
        telegramClient.execute(sendMethod);
    }

    //todo Перенести в класс TelegramMessageBuilder
    private String getIdentificationMessage(String fileName, String searchQuery, String movieOverview,
                                            int optionId, int totalOptions) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(Emoji.FOLDER.getEmoji()).append(" ")
                .append("Загружен новый файл: \n\"").append(fileName).append("\"\n\n")
                .append("Выполнен поиск фильма по фразе: \"").append(searchQuery).append("\"\n\n")
                .append(movieOverview).append("\n\n")
                .append("Описание соответствует файлу?\n")
                .append("(вариант ").append(optionId + 1).append(" из ").append(totalOptions).append(")");
        return messageBuilder.toString();
    }

    private String getNotificationTypeEmoji(NotificationType notificationType) {
        String emoji;
        switch (notificationType) {
            case INFO -> emoji = Emoji.INFO.getEmoji() + " ";
            case WARNING -> emoji = Emoji.WARNING.getEmoji() + " ";
            case ERROR -> emoji = Emoji.ERROR.getEmoji() + " ";
            case DONE -> emoji = Emoji.DONE.getEmoji() + " ";
            default -> emoji = "";
        }
        return emoji;
    }

}

package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieDtoV14;
import com.kinoved.common.telegram.dtos.ConfirmMovieIdRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import com.kinoved.common.telegram.enums.NotificationType;
import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.converters.MovieDataConverter;
import com.kinoved.telegrambot.handlers.MessageSender;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.util.Emoji;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderImpl.class);

    private final TelegramClientWrapper telegramClient;

    private final KeyboardFactory keyboardFactory;

    private final MovieDataConverter movieDataConverter;

    @Override
    public void sendMovieIdentificationMessage(Long chatId, ConfirmMovieIdRequestDto confirmMovieIdRequestDto) {
        SearchMovieDtoV14 searchMovieDto = confirmMovieIdRequestDto.getSearchMovieDtos().get(0);

        LOG.info("Sending movie identification message to chatId: {}, searchMovieDto: {}", chatId, searchMovieDto);

        String movieOverview = movieDataConverter.convertToIdentificationOverview(searchMovieDto);
        String identificationMessage = getIdentificationMessage(
                confirmMovieIdRequestDto.getMovieFileInfoDto().getFileName(),
                confirmMovieIdRequestDto.getSearchQuery(),
                movieOverview,
                0,
                confirmMovieIdRequestDto.getSearchMovieDtos().size());

        SendPhoto sendMethod = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(searchMovieDto.getPoster().getPreviewUrl()))
                .caption(identificationMessage)
                .replyMarkup(keyboardFactory.getMovieIdConfirmKeyboard(
                        searchMovieDto.getId(),
                        confirmMovieIdRequestDto.getMovieFileInfoDto().getId()))
                .build();

        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendNotificationMessage(Long chatId, SimpleNotificationDto simpleNotificationDto) {
        SendMessage sendMethod = SendMessage.builder()
                .chatId(chatId)
                .text(getNotificationTypeEmoji(simpleNotificationDto.getNotificationType())
                        + simpleNotificationDto.getMessage())
                .replyToMessageId(simpleNotificationDto.getReplyToMessageId())
                .build();
        telegramClient.execute(sendMethod);
    }

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

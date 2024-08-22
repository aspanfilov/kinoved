package com.kinoved.telegrambot.senders.impl;

import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieDtoV14;
import com.kinoved.common.telegram.dtos.MovieIdConfirmRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import com.kinoved.common.telegram.enums.NotificationType;
import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.converters.MovieDataMapper;
import com.kinoved.telegrambot.dtos.MovieDto;
import com.kinoved.telegrambot.enums.Emoji;
import com.kinoved.telegrambot.keyboard.KeyboardFactory;
import com.kinoved.telegrambot.senders.MessageSender;
import com.kinoved.telegrambot.utils.impl.MessageBuilderImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderImpl.class);

    private final TelegramClientWrapper telegramClient;

    private final KeyboardFactory keyboardFactory;

    private final MovieDataMapper movieDataMapper;

    private final MessageBuilderImpl messageBuilder;

    @Override
    public void sendMovieIdentification(Long chatId,
                                        MovieIdConfirmRequestDto movieIdConfirmRequestDto,
                                        boolean isOriginalConfirmation) {
        SearchMovieDtoV14 searchMovieDto = movieIdConfirmRequestDto.getSearchMovieDtos().get(0);

        LOG.info("Sending movie identification message to chatId: {}, searchMovieDto: {}", chatId, searchMovieDto);

        String movieOverview = movieDataMapper.convertToIdentificationOverview(searchMovieDto);
        String identificationMessage = messageBuilder.getMovieIdentificationMsg(
                movieIdConfirmRequestDto, movieOverview);

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
    public void sendNotification(Long chatId, SimpleNotificationDto simpleNotificationDto) {
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
        String shortOverview = movieDataMapper.convertToShortOverview(movieDto);
        var sendMethod = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(movieDto.getPoster().getPreviewUrl()))
                .caption(shortOverview)
                .replyMarkup(keyboardFactory.getShowMoreKeyboard(
                        movieDto.getId(),
                        movieDto.getExternalId().getKpDev()))
                .build();

        telegramClient.execute(sendMethod);
    }

    @Override
    public void notifyResponseStatus(Long chatId, Integer msgId,
                                     ResponseEntity<Void> response,
                                     String successMessage) {
        if (response.getStatusCode().is2xxSuccessful()) {
            telegramClient.execute(SendMessage.builder()
                    .chatId(chatId)
                    .replyToMessageId(msgId)
                    .text(successMessage)
                    .build());
            return;
        }
        telegramClient.execute(SendMessage.builder()
                .chatId(chatId)
                .replyToMessageId(msgId)
                .text(String.format("Произошла ошибка: %s", response.getStatusCode()))
                .build());
    }

    @Override
    public void sendMessage(Long chatId,
                            String msgText) {

        var sendMethod = SendMessage.builder()
                .chatId(chatId)
                .text(msgText)
                .build();
        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendReplyMessage(Long chatId, Integer msgId,
                                 String msgText) {

        var sendMethod = SendMessage.builder()
                .chatId(chatId)
                .replyToMessageId(msgId)
                .text(msgText)
                .build();
        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendMessageWithInlineKeyboard(Long chatId,
                                              String msgText,
                                              InlineKeyboardMarkup inlineKeyboardMarkup) {

        var sendMethod = SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(inlineKeyboardMarkup)
                .text(msgText)
                .build();
        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendMessageWithReplyKeyboard(Long chatId,
                                             String msgText,
                                             ReplyKeyboardMarkup replyKeyboardMarkup) {

        var sendMethod = SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(replyKeyboardMarkup)
                .text(msgText)
                .build();
        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendReplyMessageWithInlineKeyboard(Long chatId, Integer msgId,
                                                   String msgText,
                                                   InlineKeyboardMarkup inlineKeyboardMarkup) {

        var sendMethod = SendMessage.builder()
                .chatId(chatId)
                .replyToMessageId(msgId)
                .replyMarkup(inlineKeyboardMarkup)
                .text(msgText)
                .build();
        telegramClient.execute(sendMethod);
    }

    @Override
    public void sendReplyPhotos(Long chatId, Integer msgId, List<InputMediaPhoto> photos) {
        var sendMethod = SendMediaGroup.builder()
                .chatId(chatId)
                .replyToMessageId(msgId)
                .medias(photos)
                .build();
        telegramClient.execute(sendMethod);
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

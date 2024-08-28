package com.kinoved.telegrambot.senders;

import com.kinoved.common.telegram.dtos.MovieIdConfirmRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import com.kinoved.common.telegram.dtos.MovieDto;
import org.springframework.http.ResponseEntity;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface MessageSender {
    void sendMovieIdentification(Long chatId,
                                 MovieIdConfirmRequestDto movieIdConfirmRequestDto,
                                 boolean isOriginalConfirmation);

    void sendNotification(Long chatId, SimpleNotificationDto simpleNotificationDto);

    void sendMovieOverview(long chatId, MovieDto movieDto);

    void notifyResponseStatus(Long chatId, Integer msgId,
                              ResponseEntity<Void> response,
                              String successMessage);

    void sendMessage(Long chatId,
                     String msgText);

    void sendReplyMessage(Long chatId, Integer msgId,
                          String msgText);

    void sendMessageWithInlineKeyboard(Long chatId,
                                       String msgText,
                                       InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendMessageWithReplyKeyboard(Long chatId,
                                      String msgText,
                                      ReplyKeyboardMarkup replyKeyboardMarkup);

    void sendReplyMessageWithInlineKeyboard(Long chatId, Integer msgId,
                                            String msgText,
                                            InlineKeyboardMarkup inlineKeyboardMarkup);

    void sendReplyPhotos(Long chatId, Integer msgId, List<InputMediaPhoto> photos);
}

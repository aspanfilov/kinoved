package com.kinoved.telegrambot.handlers;

import com.kinoved.common.telegram.dtos.ConfirmMovieIdRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;

public interface MessageSender {
    void sendMovieIdentificationMessage(Long chatId, ConfirmMovieIdRequestDto confirmMovieIdRequestDto);

    void sendNotificationMessage(Long chatId, SimpleNotificationDto simpleNotificationDto);
}

package com.kinoved.telegrambot.handlers;

import com.kinoved.common.telegram.dtos.MovieIdConfirmRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import com.kinoved.telegrambot.dtos.MovieDto;

public interface MessageSender {
    void sendMovieIdentificationMessage(Long chatId,
                                        MovieIdConfirmRequestDto movieIdConfirmRequestDto,
                                        boolean isOriginalConfirmation);

    void sendNotificationMessage(Long chatId, SimpleNotificationDto simpleNotificationDto);

    void sendMovieOverview(long chatId, MovieDto movieDto);
}

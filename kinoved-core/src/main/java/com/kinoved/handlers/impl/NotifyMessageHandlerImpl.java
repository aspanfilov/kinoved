package com.kinoved.handlers.impl;

import com.kinoved.clients.KinovedTelegramBotClient;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.kafka.messages.NotifyKafkaMessage;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import com.kinoved.common.telegram.enums.NotificationType;
import com.kinoved.exceptions.UnsupportedMessageTypeException;
import com.kinoved.handlers.MovieConfirmationHandler;
import com.kinoved.handlers.NotifyMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifyMessageHandlerImpl implements NotifyMessageHandler {

    private final MovieConfirmationHandler movieConfirmationHandler;

    private final KinovedTelegramBotClient kinovedTelegramBotClient;

    @Override
    public void processMessage(NotifyKafkaMessage msg) {
        switch (msg.getMessageType()) {
            case MOVIE_FILE_INFO -> handleMovieFileInfo(msg);
            case MOVIE_FILE_MOVE_RESULT -> handleMovieFileMoveResult(msg);
            default -> throw new UnsupportedMessageTypeException(
                    "Inbound kafka message unsupported message type: " + msg.getMessageType());
        }
    }

    private void handleMovieFileInfo(NotifyKafkaMessage msg) {
        var movieFileInfoDto = (MovieFileInfoDto) msg.getBody();
        movieConfirmationHandler.saveAndRequestMovieIdConfirmation(movieFileInfoDto);
    }

    private void handleMovieFileMoveResult(NotifyKafkaMessage msg) {
        var movieFileMoveResult = (MovieFileMoveResult) msg.getBody();
        var type = movieFileMoveResult.isSuccess() ? NotificationType.DONE : NotificationType.ERROR;
        var simpleNotificationDto = SimpleNotificationDto.builder()
                .message(movieFileMoveResult.getMessage())
                .notificationType(type)
                .build();
        kinovedTelegramBotClient.sendNotification(simpleNotificationDto);
    }


}

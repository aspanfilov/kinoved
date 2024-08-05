package com.kinoved.telegrambot.controllers;

import com.kinoved.common.telegram.dtos.MovieIdConfirmRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import com.kinoved.telegrambot.config.TelegramBotProps;
import com.kinoved.telegrambot.handlers.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ActionController {

    private final MessageSender messageSender;

    private final TelegramBotProps telegramBotProps;

    @PostMapping("api/v1/actions/original-movie-id-confirm")
    public ResponseEntity<Void> sendOriginalMovieIdConfirm(
            @RequestBody MovieIdConfirmRequestDto movieIdConfirmRequestDto) {

        messageSender.sendMovieIdentificationMessage(
                telegramBotProps.getCreatorId(),
                movieIdConfirmRequestDto,
                true);

        return ResponseEntity.ok().build();
    }

    @PostMapping("api/v1/actions/translit-movie-id-confirm")
    public ResponseEntity<Void> sendTranslitMovieIdConfirm(
            @RequestBody MovieIdConfirmRequestDto movieIdConfirmRequestDto) {

        messageSender.sendMovieIdentificationMessage(
                telegramBotProps.getCreatorId(),
                movieIdConfirmRequestDto,
                false);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/actions/notification")
    public ResponseEntity<Void> sendNotification(
            @RequestBody SimpleNotificationDto simpleNotificationDto) {

        messageSender.sendNotificationMessage(
                telegramBotProps.getCreatorId(),
                simpleNotificationDto);

        return ResponseEntity.ok().build();
    }
}

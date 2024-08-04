package com.kinoved.clients;

import com.kinoved.common.telegram.dtos.ConfirmMovieIdRequestDto;
import com.kinoved.common.telegram.dtos.SimpleNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kinoved-telegram-bot", url = "${kinoved-telegram-bot.api.url}")
public interface KinovedTelegramBotClient {

    @PostMapping("api/v1/actions/confirm-movie-id")
    ResponseEntity<Void> sendMovieIdConfirm(@RequestBody ConfirmMovieIdRequestDto confirmMovieIdRequestDto);

    @PostMapping("/api/v1/actions/notification")
    ResponseEntity<Void> sendNotification(@RequestBody SimpleNotificationDto simpleNotificationDto);

}

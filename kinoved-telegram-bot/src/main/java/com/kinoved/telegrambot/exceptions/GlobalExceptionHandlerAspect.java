package com.kinoved.telegrambot.exceptions;

import com.kinoved.telegrambot.client.TelegramClientWrapper;
import com.kinoved.telegrambot.config.TelegramBotProps;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Aspect
@RequiredArgsConstructor
public class GlobalExceptionHandlerAspect {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandlerAspect.class);

    private final TelegramClientWrapper telegramClient;

    private final TelegramBotProps telegramBotProps;

    @AfterThrowing(pointcut = "execution(* com.kinoved.telegrambot..*(..))", throwing = "ex")
    public void handleAllExceptions(Exception ex) {

        LOG.error("Handling exception in aspect", ex);

        String errorMessage = String.format("Произошла ошибка: %s", ex.getMessage());
        SendMessage message = SendMessage.builder()
                .chatId(telegramBotProps.getCreatorId())
                .text(errorMessage)
                .build();

        telegramClient.execute(message);
        LOG.info("Exception handled: {}", ex.getMessage());
    }
}

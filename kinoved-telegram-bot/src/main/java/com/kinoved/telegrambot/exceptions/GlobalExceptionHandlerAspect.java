package com.kinoved.telegrambot.exceptions;

import com.kinoved.telegrambot.senders.ErrorMessageSender;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@RequiredArgsConstructor
public class GlobalExceptionHandlerAspect {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandlerAspect.class);

    private final ErrorMessageSender errorMessageSender;

    @AfterThrowing(pointcut = "execution(* com.kinoved.telegrambot..*(..))", throwing = "ex")
    public void handleAllExceptions(Exception ex) {
        LOG.error("Handling exception in aspect", ex);
        errorMessageSender.send(ex);
    }
}

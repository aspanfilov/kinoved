package com.kinoved.telegrambot.handlers.impl;

import com.kinoved.telegrambot.handlers.CallbackHandler;
import com.kinoved.telegrambot.handlers.strategies.callback.CallbackStrategy;
import com.kinoved.telegrambot.handlers.strategies.callback.UnknownCallbackStrategy;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CallbackHandlerImpl implements CallbackHandler {

    private final Map<String, CallbackStrategy> strategies;

    private final CallbackDataUtil callbackDataUtil;

    private final UnknownCallbackStrategy unknownCallbackStrategy;


    @Override
    public void replyToCallback(Long chatId, CallbackQuery callbackQuery) {
        String callbackKeyword = callbackDataUtil.getKeywordFromCallbackData(callbackQuery.getData());
        CallbackStrategy strategy = strategies.getOrDefault(callbackKeyword, unknownCallbackStrategy);
        strategy.handleCallback(chatId, callbackQuery);
    }
}

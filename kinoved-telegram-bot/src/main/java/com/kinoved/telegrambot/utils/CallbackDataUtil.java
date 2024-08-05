package com.kinoved.telegrambot.utils;

public interface CallbackDataUtil {

    String getKeywordFromCallbackData(String callbackData);

    String getParamFromCallbackData(String callback, int index);

    String createCallbackData(String key, Object... objs);

    public <T> T getObjectFromCallbackData(String callbackData, int index, Class<T> clazz);
}

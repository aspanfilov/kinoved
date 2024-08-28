package com.kinoved.telegrambot.utils.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoved.telegrambot.config.AppProps;
import com.kinoved.telegrambot.exceptions.CallbackDataException;
import com.kinoved.telegrambot.utils.CallbackDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CallbackDataUtilImpl implements CallbackDataUtil {

    private final AppProps appProps;

    private final ObjectMapper objectMapper;

    @Override
    public String getKeywordFromCallbackData(String callbackData) {
        return callbackData.split(appProps.getCallbackDataSeparator())[0];
    }

    @Override
    public String getParamFromCallbackData(String callback, int index) {
        if (index == 0) {
            throw new IllegalArgumentException("index of callback argument cannot be 0");
        }
        return callback.split(appProps.getCallbackDataSeparator())[index];
    }

//    @Override
//    public String createCallbackData(String key, Object... objs) {
//        try {
//            StringBuilder sb = new StringBuilder(key);
//            for (Object obj : objs) {
//                String json = objectMapper.writeValueAsString(obj);
//                sb.append(appProps.getCallbackDataSeparator()).append(json);
//            }
//            return sb.toString();
//        } catch (JsonProcessingException e) {
//            throw new CallbackDataException("Error serializing callback data", e);
//        }
//    }

    @Override
    public String createCallbackData(String key, String... params) {
        StringBuilder sb = new StringBuilder(key);
        for (String param : params) {
            sb.append(appProps.getCallbackDataSeparator()).append(param);
        }
        return sb.toString();
    }

//    @Override
//    public <T> T getObjectFromCallbackData(String callbackData, int index, Class<T> clazz) {
//        try {
//            String[] parts = callbackData.split(appProps.getCallbackDataSeparator());
//            if (parts.length <= index) {
//                throw new IllegalArgumentException("Invalid callback data format or index");
//            }
//            return objectMapper.readValue(parts[index], clazz);
//        } catch (JsonProcessingException e) {
//            throw new CallbackDataException("Error deserializing callback data", e);
//        }
//    }

}

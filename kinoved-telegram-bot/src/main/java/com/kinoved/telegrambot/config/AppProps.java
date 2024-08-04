package com.kinoved.telegrambot.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.settings")
@Getter
@RequiredArgsConstructor
public class AppProps {
    private final String callbackDataSeparator;

    private final String undefinedGenreFolder;

    private final int captionSymbolsLimit;

    private final int maxDisplayedPersonsPerProfession;
}

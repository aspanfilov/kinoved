package com.kinoved.config.props;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kinopoisk.api")
@Getter
@RequiredArgsConstructor
public class KinopoiskProps {

    private final String url;

    private final String token;

    private final String version;

    private final Paths paths;

    @Data
    public static class Paths {
        private final String searchMovies;

        private final String movieDetails;

        private final String imageDetails;
    }
}

package com.kinoved.config;

import com.kinoved.config.props.KinopoiskProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@EnableConfigurationProperties(KinopoiskProps.class)
@Configuration
public class WebClientConfig {

    @Autowired
    private KinopoiskProps kinopoiskProperties;

    @Bean
    public WebClient kinopoiskWebClient() {
        return WebClient.builder()
                .baseUrl(kinopoiskProperties.getUrl())
                .defaultHeader("X-API-KEY", kinopoiskProperties.getToken())
                .defaultHeader("Content-Type", "application/json")
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers().forEach(
                    (name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("Response Status: " + clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders().forEach(
                    (name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientResponse);
        });
    }

}

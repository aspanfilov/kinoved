package com.kinoved.telegrambot.client;

import com.kinoved.common.telegram.dtos.ConfirmMovieIdResponseDto;
import com.kinoved.telegrambot.dtos.MovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "kinoved-core", url = "${kinoved-core.api.url}")
public interface KinovedCoreClient {

    @GetMapping("/api/v1/movies/first")
    MovieDto getFirstMovie();

    @GetMapping("/api/v1/movies")
    List<MovieDto> getAllMovies();

    @GetMapping("/api/v1/movies/{id}")
    MovieDto getMovieById(@PathVariable("id") String id);


    @PostMapping("/api/v1/actions/movie-id-confirmation")
    ResponseEntity<Void> handleMovieIdConfirmation(@RequestBody ConfirmMovieIdResponseDto confirmMovieIdResponseDto);
}

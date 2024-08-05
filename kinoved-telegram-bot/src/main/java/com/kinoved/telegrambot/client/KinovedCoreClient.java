package com.kinoved.telegrambot.client;

import com.kinoved.common.enums.MovieFileStatus;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.telegram.dtos.MovieIdConfirmResponseDto;
import com.kinoved.telegrambot.dtos.MovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "kinoved-core", url = "${kinoved-core.api.url}")
public interface KinovedCoreClient {

    @GetMapping("/api/v1/movies/first")
    MovieDto getFirstMovie();

    @GetMapping("/api/v1/movies")
    List<MovieDto> getAllMovies(@RequestParam(required = false) String genre);

    @GetMapping("/api/v1/movies/{id}")
    MovieDto getMovieById(@PathVariable("id") String id);


    @GetMapping("/api/v1/genres")
    List<String> getGenres();


    @GetMapping("/api/v1/files-info/")
    List<MovieFileInfoDto> getMovieFilesInfo(@RequestParam(required = true) List<MovieFileStatus> statuses);


    @PostMapping("/api/v1/actions/movie-id-confirmation")
    ResponseEntity<Void> handleMovieIdConfirmation(@RequestBody MovieIdConfirmResponseDto movieIdConfirmResponseDto);
}

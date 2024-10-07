package com.kinoved.telegrambot.client;

import com.kinoved.common.enums.MovieFileStatus;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.telegram.dtos.MovieDto;
import com.kinoved.common.telegram.dtos.MovieIdConfirmResponseDto;
import com.kinoved.common.telegram.dtos.MovieImageDto;
import com.kinoved.common.telegram.dtos.SortPreferenceDto;
import com.kinoved.common.telegram.dtos.UpdateMovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "kinoved-core", url = "${kinoved-core.api.url}")
public interface KinovedCoreClient {

    @GetMapping("/api/v1/movies")
    List<MovieDto> getMovies(
            @RequestHeader("X-User-ID") Long userId,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) Boolean last,
            @RequestParam(required = false) Boolean favorite,
            @RequestParam(required = false) Boolean watched);

    @GetMapping("/api/v1/movies/{id}")
    MovieDto getMovieById(@PathVariable("id") String id);

    @RequestMapping(value = "/api/v1/movies/{id}", method = RequestMethod.PATCH)
    MovieDto updateMovie(@PathVariable("id") String id,
                         @RequestBody UpdateMovieDto updateMovieDto);

    @GetMapping("/api/v1/movies/{movieId}/images")
    List<MovieImageDto> getMovieImages(
            @PathVariable("movieId") Long movieId,
            @RequestParam("types") List<String> types);


    @GetMapping("/api/v1/genres")
    List<String> getGenres();


    @GetMapping("/api/v1/sort")
    SortPreferenceDto getSortPreference(
            @RequestHeader("X-User-ID") Long userId);

    @PostMapping("/api/v1/sort")
    ResponseEntity<Void> setSortPreference(
            @RequestHeader("X-User-ID") Long userId,
            @RequestBody SortPreferenceDto sortPreferenceDto);


    @GetMapping("/api/v1/files-info/")
    List<MovieFileInfoDto> getMovieFilesInfo(
            @RequestParam(required = true) List<MovieFileStatus> statuses);


    @PostMapping("/api/v1/actions/movie-id-confirmation")
    ResponseEntity<Void> handleMovieIdConfirmation(
            @RequestBody MovieIdConfirmResponseDto movieIdConfirmResponseDto);
}

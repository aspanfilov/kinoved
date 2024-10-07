package com.kinoved.controllers;

import com.kinoved.exceptions.EntityNotFoundException;
import com.kinoved.models.MovieData;
import com.kinoved.services.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieDataController {

    private final MovieDataService movieDataService;

    @GetMapping("/api/v1/movies")
    public List<MovieData> getMovies() {
        return movieDataService.getAllMovies();
    }

    @GetMapping("/api/v1/movies/{id}")
    public MovieData getMovieById(@PathVariable("id") String id) {
        return movieDataService.getMovieById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id %s not found".formatted(id)));
    }

    @GetMapping("/api/v1/movies/first")
    public MovieData getFirstMovie() {
        return movieDataService.getAllMovies().get(0);
    }
}

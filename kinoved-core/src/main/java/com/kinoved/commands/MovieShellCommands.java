package com.kinoved.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoved.clients.KinopoiskClient;
import com.kinoved.common.kinopoisk.dtos.movies.MovieDtoV14;
import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieResponseDtoV14;
import com.kinoved.models.MovieData;
import com.kinoved.services.MovieDataLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@RequiredArgsConstructor
@ShellComponent
public class MovieShellCommands {

    private final KinopoiskClient kinopoiskClient;

    private final MovieDataLoaderService movieService;

    private final ObjectMapper objectMapper;

    @ShellMethod(value = "Search for a movie by title", key = {"sm", "search-movie"})
    public String searchMovie(
            @ShellOption(value = {"-q", "--query"}, help = "Title of the movie")
            String query,
            @ShellOption(value = {"-p", "--page"}, defaultValue = "1", help = "Page number")
            Integer page,
            @ShellOption(value = {"-l", "--limit"}, defaultValue = "10", help = "Number of results per page")
            Integer limit) throws JsonProcessingException {

        SearchMovieResponseDtoV14 response = kinopoiskClient.searchMovie(query, page, limit);
        return objectMapper.writeValueAsString(response);
    }

    @ShellMethod(value = "Get movie by id", key = {"gm", "get-movie"})
    public String getMovieById(
            @ShellOption(value = {"-i", "--id"}, help = "Id of the movie")
            Long id) throws JsonProcessingException {

        MovieDtoV14 response = kinopoiskClient.getMovieById(id);
        return objectMapper.writeValueAsString(response);
    }

    @ShellMethod(value = "Get and save movie by id", key = {"gsm", "get-save-movie"})
    public String getAndSaveMovieById(
            @ShellOption(value = {"-i", "--id"}, help = "Id of the movie")
            Long id) throws JsonProcessingException {

        MovieData movieData = movieService.loadAndSaveMovieDataByKpDevId(id);
        return movieData.toString();
    }

}

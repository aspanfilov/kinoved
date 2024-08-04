package com.kinoved.controllers;

import com.kinoved.common.telegram.dtos.ConfirmMovieIdResponseDto;
import com.kinoved.handlers.MovieConfirmationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ActionController {

    private final MovieConfirmationHandler movieConfirmationHandler;

    @PostMapping("/api/v1/actions/movie-id-confirmation")
    public ResponseEntity<Void> handleMovieIdConfirmation(
            @RequestBody ConfirmMovieIdResponseDto confirmMovieIdResponseDto) {
        movieConfirmationHandler.handleMovieIdConfirmationResponse(confirmMovieIdResponseDto);
        return ResponseEntity.ok().build();
    }

}

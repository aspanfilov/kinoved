package com.kinoved.handlers;

import com.kinoved.common.telegram.dtos.ConfirmMovieIdResponseDto;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;

public interface MovieConfirmationHandler {
    void saveAndRequestMovieIdConfirmation(MovieFileInfoDto movieFileInfoDto);

    void handleMovieIdConfirmationResponse(ConfirmMovieIdResponseDto confirmMovieIdResponseDto);
}

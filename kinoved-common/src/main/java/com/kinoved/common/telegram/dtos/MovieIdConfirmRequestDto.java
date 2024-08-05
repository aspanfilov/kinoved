package com.kinoved.common.telegram.dtos;

import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieDtoV14;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieIdConfirmRequestDto {
    private String searchQuery;
    private List<SearchMovieDtoV14> searchMovieDtos;
    private MovieFileInfoDto movieFileInfoDto;
    private Integer messageId;
}

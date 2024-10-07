package com.kinoved.telegrambot.utils;

import com.kinoved.common.enums.SortField;
import com.kinoved.common.enums.SortOrder;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.telegram.dtos.MovieIdConfirmRequestDto;
import com.kinoved.common.telegram.dtos.SortPreferenceDto;

import java.util.List;

public interface MessageBuilder {

    String getUnfinishedFilesMsg(List<MovieFileInfoDto> movieFileInfoDtos);

    String getSortRequestMsg(SortPreferenceDto sortPreferenceDto);

    String getMovieIdentificationMsg(
            MovieIdConfirmRequestDto movieIdConfirmRequestDto,
            String movieOverview);

    String getSortSettingMsg(SortField sortField, SortOrder sortOrder);
}

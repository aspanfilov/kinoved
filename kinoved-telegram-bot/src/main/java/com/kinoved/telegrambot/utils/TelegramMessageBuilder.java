package com.kinoved.telegrambot.utils;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;

import java.util.List;

public interface TelegramMessageBuilder {

    String getUnfinishedFilesMsg(List<MovieFileInfoDto> movieFileInfoDtos);
}

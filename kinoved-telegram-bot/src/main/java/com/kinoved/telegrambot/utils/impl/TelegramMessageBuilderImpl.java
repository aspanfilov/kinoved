package com.kinoved.telegrambot.utils.impl;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.telegrambot.enums.Emoji;
import com.kinoved.telegrambot.utils.TelegramMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

//todo перенести сюда формирование представления фильма

@Component
@RequiredArgsConstructor
public class TelegramMessageBuilderImpl implements TelegramMessageBuilder {

    public String getUnfinishedFilesMsg(List<MovieFileInfoDto> movieFileInfoDtos) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(Emoji.REPORT.getEmoji()).append(" Список незавершенных файлов фильмов:\n\n");

        for (MovieFileInfoDto fileInfo : movieFileInfoDtos) {
            messageBuilder.append(fileInfo.getStatus()).append(": ")
                    .append(fileInfo.getFileName()).append("\n\n");
        }
        return messageBuilder.toString();
    }
}

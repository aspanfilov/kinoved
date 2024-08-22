package com.kinoved.telegrambot.utils.impl;

import com.kinoved.common.enums.SortField;
import com.kinoved.common.enums.SortOrder;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.telegram.dtos.MovieIdConfirmRequestDto;
import com.kinoved.common.telegram.dtos.SortPreferenceDto;
import com.kinoved.telegrambot.enums.Emoji;
import com.kinoved.telegrambot.utils.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

//todo перенести сюда формирование представления фильма

@Component
@RequiredArgsConstructor
public class MessageBuilderImpl implements MessageBuilder {

    @Override
    public String getUnfinishedFilesMsg(List<MovieFileInfoDto> movieFileInfoDtos) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(Emoji.REPORT.getEmoji()).append(" Список незавершенных файлов фильмов:\n\n");

        for (MovieFileInfoDto fileInfo : movieFileInfoDtos) {
            messageBuilder.append(fileInfo.getStatus()).append(": ")
                    .append(fileInfo.getFileName()).append("\n\n");
        }
        return messageBuilder.toString();
    }

    @Override
    public String getSortRequestMsg(SortPreferenceDto sortPreferenceDto) {
        String sortField = formatSortField(sortPreferenceDto.getSortField());
        String sortOrder = sortPreferenceDto.getSortOrder() == SortOrder.ASC
                ? "по возрастанию"
                : "по убыванию";

        return String.format(
                "Текущая сортировка: \"%s %s.\"\n\nВыберите желаемую сортировку:",
                sortField, sortOrder
        );
    }

    @Override
    public String getMovieIdentificationMsg(
            MovieIdConfirmRequestDto movieIdConfirmRequestDto,
            String movieOverview) {

        String filename = movieIdConfirmRequestDto.getMovieFileInfoDto().getFileName();
        String searchQuery = movieIdConfirmRequestDto.getSearchQuery();
        int optionId = 0;
        int totalOptions = movieIdConfirmRequestDto.getSearchMovieDtos().size();

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(Emoji.FOLDER.getEmoji()).append(" ")
                .append("Загружен новый файл: \n\"").append(filename).append("\"\n\n")
                .append("Выполнен поиск фильма по фразе: \"").append(searchQuery).append("\"\n\n")
                .append(movieOverview).append("\n\n")
                .append("Описание соответствует файлу?\n")
                .append("(вариант ").append(optionId + 1).append(" из ").append(totalOptions).append(")");
        return messageBuilder.toString();
    }

    @Override
    public String getSortSettingMsg(SortField sortField, SortOrder sortOrder) {
        String sortFieldText = switch (sortField) {
            case RATING -> "по рейтингу";
            case DOWNLOAD_DATE -> "по дате загрузки";
        };

        String sortOrderText = switch (sortOrder) {
            case ASC -> "в порядке возрастания";
            case DESC -> "в порядке убывания";
        };

        return String.format("Установлена сортировка \"%s %s\"", sortFieldText, sortOrderText);
    }

    private String formatSortField(SortField sortField) {
        return switch (sortField) {
            case RATING -> "рейтинг";
            case DOWNLOAD_DATE -> "дата создания";
        };
    }
}

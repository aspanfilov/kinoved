package com.kinoved.mappers;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.enums.MovieFileStatus;
import com.kinoved.models.MovieFileInfo;
import org.springframework.stereotype.Component;

@Component
public class MovieFileInfoMapper {

    public MovieFileInfo toNewEntity(MovieFileInfoDto dto) {
        if (dto == null) {
            return null;
        }
        return MovieFileInfo.builder()
                .id(null)
                .fileName(dto.getFileName())
                .status(MovieFileStatus.NEW)
                .title(dto.getTitle())
                .translatedTitle(dto.getTranslatedTitle())
                .year(dto.getYear())
                .resolution(dto.getResolution())
                .fileSizeInMb(dto.getFileSizeInMb())
                .createdAt(null)
                .updatedAt(null)
                .build();
    }

    public MovieFileInfoDto toDto(MovieFileInfo movieFileInfo) {
        if (movieFileInfo == null) {
            return null;
        }
        return MovieFileInfoDto.builder()
                .id(movieFileInfo.getId())
                .fileName(movieFileInfo.getFileName())
                .status(movieFileInfo.getStatus().toString())
                .title(movieFileInfo.getTitle())
                .translatedTitle(movieFileInfo.getTranslatedTitle())
                .year(movieFileInfo.getYear())
                .resolution(movieFileInfo.getResolution())
                .fileSizeInMb(movieFileInfo.getFileSizeInMb())
                .createdAt(movieFileInfo.getCreatedAt())
                .updatedAt(movieFileInfo.getUpdatedAt())
                .build();
    }
}

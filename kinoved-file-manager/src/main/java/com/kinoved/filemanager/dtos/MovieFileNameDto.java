package com.kinoved.filemanager.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFileNameDto {
    private String fileName;
    private String title;
    private String year;
    private String resolution;

    public MovieFileNameDto(String fileName) {
        this.fileName = fileName;
    }
}

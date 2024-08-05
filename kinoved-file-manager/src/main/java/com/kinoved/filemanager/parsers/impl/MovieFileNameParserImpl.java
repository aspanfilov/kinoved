package com.kinoved.filemanager.parsers.impl;

import com.kinoved.filemanager.dtos.MovieFileNameDto;
import com.kinoved.filemanager.parsers.MovieFileNameParser;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MovieFileNameParserImpl implements MovieFileNameParser {

    private static final String NAME_YEAR_RESOLUTION_REGEX =
            "^(.*?)[.\\s]?(?:\\(?(\\d{4})\\)?)[.\\s]?(.*?)(\\d{3,4}p).*";

    private static final Pattern NAME_YEAR_RESOLUTION_PATTERN = Pattern.compile(NAME_YEAR_RESOLUTION_REGEX);

    @Override
    public MovieFileNameDto parseFileName(String fileName) {
        var movieFileNameDto = new MovieFileNameDto(fileName);

        var matcher = NAME_YEAR_RESOLUTION_PATTERN.matcher(fileName);
        if (matcher.matches()) {
            movieFileNameDto.setTitle(matcher.group(1).replace('.', ' ').trim());
            movieFileNameDto.setYear(matcher.group(2));
            movieFileNameDto.setResolution(matcher.group(4));
            return movieFileNameDto;
        }

        var fileNameWithoutExtension = removeFileExtension(fileName);
        movieFileNameDto.setTitle(fileNameWithoutExtension);
        return movieFileNameDto;
    }

    private String removeFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }
}

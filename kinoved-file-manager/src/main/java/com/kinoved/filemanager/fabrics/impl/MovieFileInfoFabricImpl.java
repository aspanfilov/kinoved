package com.kinoved.filemanager.fabrics.impl;

import com.ibm.icu.text.Transliterator;
import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.filemanager.dtos.MovieFileNameDto;
import com.kinoved.filemanager.fabrics.MovieFileInfoFabric;
import com.kinoved.filemanager.parsers.impl.MovieFileNameParser;
import com.kinoved.filemanager.utils.impl.FileSystemUtilityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
public class MovieFileInfoFabricImpl implements MovieFileInfoFabric {

    private static final Transliterator TO_RUSSIAN = Transliterator.getInstance("Latin-Russian/BGN");

    private final FileSystemUtilityImpl fileSystemUtility;

    private final MovieFileNameParser movieFileNameParser;

    @Override
    public MovieFileInfoDto create(File file) {
        MovieFileNameDto nameDto = movieFileNameParser.parseFileName(file.getName());
        return MovieFileInfoDto.builder()
                .fileName(nameDto.getFileName())
                .title(nameDto.getTitle())
                .translatedTitle(translateToRussian(nameDto.getTitle()))
                .year(nameDto.getYear())
                .resolution(nameDto.getResolution())
                .fileSizeInMb(fileSystemUtility.getFileSizeInMb(file))
                .build();
    }

    private String translateToRussian(String text) {
        return TO_RUSSIAN.transliterate(text);
    }

}

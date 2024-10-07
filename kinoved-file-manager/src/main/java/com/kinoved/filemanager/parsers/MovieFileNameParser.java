package com.kinoved.filemanager.parsers;

import com.kinoved.filemanager.dtos.MovieFileNameDto;

public interface MovieFileNameParser {
    MovieFileNameDto parseFileName(String fileName);
}

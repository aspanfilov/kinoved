package com.kinoved.filemanager.parsers.impl;

import com.kinoved.filemanager.dtos.MovieFileNameDto;

public interface MovieFileNameParser {
    MovieFileNameDto parseFileName(String fileName);
}

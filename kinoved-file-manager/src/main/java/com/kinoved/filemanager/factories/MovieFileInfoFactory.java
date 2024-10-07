package com.kinoved.filemanager.factories;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;

import java.io.File;

public interface MovieFileInfoFactory {
    MovieFileInfoDto create(File file);
}

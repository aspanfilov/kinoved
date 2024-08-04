package com.kinoved.filemanager.fabrics;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;

import java.io.File;

public interface MovieFileInfoFabric {
    MovieFileInfoDto create(File file);
}

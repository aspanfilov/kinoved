package com.kinoved.filemanager.handlers;

import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;

import java.io.File;

public interface FileTaskHandler {
    void readFileInfoAndSendToKafka(File file);

    void moveFileToGenreDirectory(MovieFileMoveTask movieFileMoveTask);

}

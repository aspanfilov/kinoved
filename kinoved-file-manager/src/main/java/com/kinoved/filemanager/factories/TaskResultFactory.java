package com.kinoved.filemanager.factories;

import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;

public interface TaskResultFactory {
    MovieFileMoveResult create(MovieFileMoveTask movieFileMoveTask,
                               boolean isSuccess, String message);
}

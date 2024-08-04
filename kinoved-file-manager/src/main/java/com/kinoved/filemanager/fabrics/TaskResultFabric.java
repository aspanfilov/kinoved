package com.kinoved.filemanager.fabrics;

import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;

public interface TaskResultFabric {
    MovieFileMoveResult create(MovieFileMoveTask movieFileMoveTask,
                               boolean isSuccess, String message);
}

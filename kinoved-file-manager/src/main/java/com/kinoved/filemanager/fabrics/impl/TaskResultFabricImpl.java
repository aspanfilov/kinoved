package com.kinoved.filemanager.fabrics.impl;

import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;
import com.kinoved.filemanager.fabrics.TaskResultFabric;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskResultFabricImpl implements TaskResultFabric {

    @Override
    public MovieFileMoveResult create(MovieFileMoveTask movieFileMoveTask,
                                      boolean isSuccess, String message) {
        return MovieFileMoveResult.builder()
                .movieFileInfoId(movieFileMoveTask.getMovieFileInfoId())
                .fileName(movieFileMoveTask.getFileName())
                .genre(movieFileMoveTask.getGenre())
                .success(isSuccess)
                .message(message)
                .build();
    }
}

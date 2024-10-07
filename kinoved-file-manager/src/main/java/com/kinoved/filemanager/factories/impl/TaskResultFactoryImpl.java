package com.kinoved.filemanager.factories.impl;

import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;
import com.kinoved.filemanager.factories.TaskResultFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskResultFactoryImpl implements TaskResultFactory {

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

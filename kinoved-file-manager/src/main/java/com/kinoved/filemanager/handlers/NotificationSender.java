package com.kinoved.filemanager.handlers;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;

public interface NotificationSender {
    void sendTaskResultNotification(MovieFileMoveTask movieFileMoveTask, boolean isSuccess, String message);

    void sendNotification(MovieFileInfoDto movieFileInfoDto);
}

package com.kinoved.filemanager.handlers.impl;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;
import com.kinoved.common.kafka.messages.TaskResultKafkaMessage;
import com.kinoved.filemanager.fabrics.TaskResultFabric;
import com.kinoved.filemanager.handlers.NotificationSender;
import com.kinoved.filemanager.kafka.MessageSender;
import com.kinoved.filemanager.kafka.NotifyKafkaMessageFabric;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSenderImpl implements NotificationSender {

    private final MessageSender messageSender;

    private final TaskResultFabric taskResultFabric;

    private final NotifyKafkaMessageFabric notifyKafkaMessageFabric;

    @Override
    public void sendTaskResultNotification(MovieFileMoveTask movieFileMoveTask, boolean isSuccess, String message) {
        MovieFileMoveResult movieFileMoveResult = taskResultFabric.create(movieFileMoveTask, isSuccess, message);
        TaskResultKafkaMessage notifyMessage = notifyKafkaMessageFabric.createMessage(movieFileMoveResult);
        messageSender.send(notifyMessage);
    }

    @Override
    public void sendNotification(MovieFileInfoDto movieFileInfoDto) {
        TaskResultKafkaMessage notifyMessage = notifyKafkaMessageFabric.createMessage(movieFileInfoDto);
        messageSender.send(notifyMessage);
    }
}

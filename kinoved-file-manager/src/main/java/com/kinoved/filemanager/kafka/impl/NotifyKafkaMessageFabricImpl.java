package com.kinoved.filemanager.kafka.impl;

import com.kinoved.common.filemanager.dtos.MovieFileInfoDto;
import com.kinoved.common.filemanager.dtos.MovieFileMoveResult;
import com.kinoved.common.kafka.MessageBody;
import com.kinoved.common.kafka.messages.TaskResultKafkaMessage;
import com.kinoved.common.kafka.enums.NotifyMessageType;
import com.kinoved.filemanager.exceptions.UnsupportedMessageTypeException;
import com.kinoved.filemanager.kafka.NotifyKafkaMessageFabric;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotifyKafkaMessageFabricImpl implements NotifyKafkaMessageFabric {
    @Override
    public TaskResultKafkaMessage createMessage(MessageBody body) {
        return TaskResultKafkaMessage.builder()
                .id(UUID.randomUUID())
                .messageDate(LocalDateTime.now())
                .messageType(getEventType(body))
                .body(body)
                .build();
    }

    private NotifyMessageType getEventType(MessageBody body) {
        if (body instanceof MovieFileInfoDto) {
            return NotifyMessageType.MOVIE_FILE_INFO;
        } else if (body instanceof MovieFileMoveResult) {
            return NotifyMessageType.MOVIE_FILE_MOVE_RESULT;
        } else {
            throw new UnsupportedMessageTypeException(
                    "Outbound kafka message unsupported body type: " + body.getClass().getSimpleName());
        }
    }
}

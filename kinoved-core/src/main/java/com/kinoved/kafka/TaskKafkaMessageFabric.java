package com.kinoved.kafka;

import com.kinoved.common.filemanager.dtos.MovieFileMoveTask;
import com.kinoved.common.kafka.MessageBody;
import com.kinoved.common.kafka.enums.TaskMessageType;
import com.kinoved.common.kafka.messages.TaskKafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskKafkaMessageFabric {
    public TaskKafkaMessage createMessage(MessageBody body) {
        return TaskKafkaMessage.builder()
                .id(UUID.randomUUID())
                .messageDate(LocalDateTime.now())
                .messageType(getEventType(body))
                .body(body)
                .build();
    }

    private TaskMessageType getEventType(MessageBody body) {
        if (body instanceof MovieFileMoveTask) {
            return TaskMessageType.MOVIE_FILE_MOVE_TASK;
        } else {
            throw new UnsupportedOperationException(
                    "Outbound kafka message unsupported body type: " + body.getClass().getSimpleName());
        }
    }
}

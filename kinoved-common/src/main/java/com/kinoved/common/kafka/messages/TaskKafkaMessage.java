package com.kinoved.common.kafka.messages;

import com.kinoved.common.kafka.MessageBody;
import com.kinoved.common.kafka.enums.TaskMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskKafkaMessage {
    private UUID id;

    private LocalDateTime messageDate;

    private TaskMessageType messageType;

    private MessageBody body;
}

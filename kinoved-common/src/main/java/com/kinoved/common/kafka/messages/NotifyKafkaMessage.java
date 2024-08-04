package com.kinoved.common.kafka.messages;

import com.kinoved.common.kafka.MessageBody;
import com.kinoved.common.kafka.enums.NotifyMessageType;
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
public class NotifyKafkaMessage {
    private UUID id;

    private LocalDateTime messageDate;

    private NotifyMessageType messageType;

    private MessageBody body;
}

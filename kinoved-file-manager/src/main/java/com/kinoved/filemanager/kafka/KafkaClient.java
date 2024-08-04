package com.kinoved.filemanager.kafka;

import com.kinoved.filemanager.handlers.MessageHandler;
import com.kinoved.common.kafka.messages.TaskKafkaMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaClient {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaClient.class);

    private final MessageHandler messageHandler;

    @KafkaListener(
            topics = "${app.kafka.topics.file-task.name}",
            containerFactory = "listenerContainerFactory")
    public void listen(@Payload List<TaskKafkaMessage> taskKafkaMessages) {
        LOG.info("messages received: {}", taskKafkaMessages);

        for (TaskKafkaMessage msg : taskKafkaMessages) {
            LOG.info("inboundKafkaMessage with type {} and id {}", msg.getMessageType(), msg.getId());

            messageHandler.processMessage(msg);
        }
    }

}

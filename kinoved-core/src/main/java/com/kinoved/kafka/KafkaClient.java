package com.kinoved.kafka;

import com.kinoved.common.kafka.messages.NotifyKafkaMessage;
import com.kinoved.handlers.NotifyMessageHandler;
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

    private final NotifyMessageHandler notifyMessageHandler;

    @KafkaListener(
            topics = "${app.kafka.topics.file-notify.name}",
            containerFactory = "listenerContainerFactory")
    public void listen(@Payload List<NotifyKafkaMessage> notifyKafkaMessages) {
        LOG.info("messages received: {}", notifyKafkaMessages);

        for (NotifyKafkaMessage msg : notifyKafkaMessages) {
            LOG.info("inboundKafkaMessage with type {} and id {}", msg.getMessageType(), msg.getId());

            notifyMessageHandler.processMessage(msg);
        }
    }
}

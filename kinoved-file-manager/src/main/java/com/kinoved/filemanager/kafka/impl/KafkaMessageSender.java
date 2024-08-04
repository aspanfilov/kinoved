package com.kinoved.filemanager.kafka.impl;

import com.kinoved.filemanager.config.props.KafkaProps;
import com.kinoved.common.kafka.messages.NotifyKafkaMessage;
import com.kinoved.filemanager.kafka.MessageSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

//todo использовать отдельный executor для send.
// Потому что если кафка недоступна, то метод send будет блокироваться, и логи не запишутся

@Component
@RequiredArgsConstructor
public class KafkaMessageSender implements MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageSender.class);

    private final KafkaProps kafkaProps;

    private final KafkaTemplate<String, NotifyKafkaMessage> kafkaTemplate;

    @Override
    public void send(NotifyKafkaMessage notifyKafkaMessage) {
        try {
            LOG.info("Kafka message with type: {} and id: {}",
                    notifyKafkaMessage.getMessageType(), notifyKafkaMessage.getId());

            kafkaTemplate.send(kafkaProps.getFileNotifyTopicName(), notifyKafkaMessage)
                    .whenComplete((sendResult, ex) -> {
                        if (ex == null) {
                            LOG.info(
                                    "message id:{} was sent, offset:{}",
                                    notifyKafkaMessage.getId(),
                                    sendResult.getRecordMetadata().offset());
                        } else {
                            LOG.error("Kafka message with type: {} and id: {} was not sent",
                                    notifyKafkaMessage.getMessageType(), notifyKafkaMessage.getId(), ex);
                        }
                    });
        } catch (Exception ex) {
            LOG.error("Broker error! Kafka message with type: {} and id: {} was not sent",
                    notifyKafkaMessage.getMessageType(), notifyKafkaMessage.getId(), ex);
        }
    }
}

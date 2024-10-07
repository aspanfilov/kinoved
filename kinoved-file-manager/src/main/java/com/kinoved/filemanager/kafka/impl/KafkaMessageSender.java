package com.kinoved.filemanager.kafka.impl;

import com.kinoved.filemanager.config.props.KafkaProps;
import com.kinoved.common.kafka.messages.TaskResultKafkaMessage;
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

    private final KafkaTemplate<String, TaskResultKafkaMessage> kafkaTemplate;

    @Override
    public void send(TaskResultKafkaMessage taskResultKafkaMessage) {
        try {
            LOG.info("Kafka message with type: {} and id: {}",
                    taskResultKafkaMessage.getMessageType(), taskResultKafkaMessage.getId());

            kafkaTemplate.send(kafkaProps.getFileNotifyTopicName(), taskResultKafkaMessage)
                    .whenComplete((sendResult, ex) -> {
                        if (ex == null) {
                            LOG.info(
                                    "message id:{} was sent, offset:{}",
                                    taskResultKafkaMessage.getId(),
                                    sendResult.getRecordMetadata().offset());
                        } else {
                            LOG.error("Kafka message with type: {} and id: {} was not sent",
                                    taskResultKafkaMessage.getMessageType(), taskResultKafkaMessage.getId(), ex);
                        }
                    });
        } catch (Exception ex) {
            LOG.error("Broker error! Kafka message with type: {} and id: {} was not sent",
                    taskResultKafkaMessage.getMessageType(), taskResultKafkaMessage.getId(), ex);
        }
    }
}

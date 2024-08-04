package com.kinoved.kafka;

import com.kinoved.common.kafka.messages.TaskKafkaMessage;
import com.kinoved.config.props.KafkaProps;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageSender implements MessageSender {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageSender.class);

    private final KafkaProps kafkaProps;

    private final KafkaTemplate<String, TaskKafkaMessage> kafkaTemplate;

    @Override
    public void send(TaskKafkaMessage taskKafkaMessage) {
        try {
            LOG.info("Kafka message with type: {} and id: {}",
                    taskKafkaMessage.getMessageType(), taskKafkaMessage.getId());

            kafkaTemplate.send(kafkaProps.getFileTaskTopicName(), taskKafkaMessage)
                    .whenComplete((sendResult, ex) -> {
                        if (ex == null) {
                            LOG.info(
                                    "message id:{} was sent, offset:{}",
                                    taskKafkaMessage.getId(),
                                    sendResult.getRecordMetadata().offset());
                        } else {
                            LOG.error("Kafka message with type: {} and id: {} was not sent",
                                    taskKafkaMessage.getMessageType(), taskKafkaMessage.getId(), ex);
                        }
                    });
        } catch (Exception ex) {
            LOG.error("Broker error! Kafka message with type: {} and id: {} was not sent",
                    taskKafkaMessage.getMessageType(), taskKafkaMessage.getId(), ex);
        }
    }
}

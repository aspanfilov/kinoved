package com.kinoved.kafka;

import com.kinoved.common.kafka.messages.TaskKafkaMessage;

public interface MessageSender {
    void send(TaskKafkaMessage taskKafkaMessage);
}

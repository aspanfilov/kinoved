package com.kinoved.filemanager.kafka;

import com.kinoved.common.kafka.messages.TaskResultKafkaMessage;

public interface MessageSender {
    void send(TaskResultKafkaMessage taskResultKafkaMessage);
}

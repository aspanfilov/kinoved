package com.kinoved.filemanager.kafka;

import com.kinoved.common.kafka.messages.NotifyKafkaMessage;

public interface MessageSender {
    void send(NotifyKafkaMessage notifyKafkaMessage);
}

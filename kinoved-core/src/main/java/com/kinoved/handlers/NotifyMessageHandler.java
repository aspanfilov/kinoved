package com.kinoved.handlers;

import com.kinoved.common.kafka.messages.NotifyKafkaMessage;

public interface NotifyMessageHandler {
    void processMessage(NotifyKafkaMessage msg);
}

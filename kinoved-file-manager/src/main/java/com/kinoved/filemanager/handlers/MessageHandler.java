package com.kinoved.filemanager.handlers;

import com.kinoved.common.kafka.messages.TaskKafkaMessage;

public interface MessageHandler {
    void processMessage(TaskKafkaMessage msg);
}

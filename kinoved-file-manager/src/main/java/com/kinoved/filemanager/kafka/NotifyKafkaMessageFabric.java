package com.kinoved.filemanager.kafka;

import com.kinoved.common.kafka.MessageBody;
import com.kinoved.common.kafka.messages.NotifyKafkaMessage;

public interface NotifyKafkaMessageFabric {
    NotifyKafkaMessage createMessage(MessageBody body);
}

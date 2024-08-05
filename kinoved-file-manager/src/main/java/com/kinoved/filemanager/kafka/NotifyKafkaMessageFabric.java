package com.kinoved.filemanager.kafka;

import com.kinoved.common.kafka.MessageBody;
import com.kinoved.common.kafka.messages.TaskResultKafkaMessage;

public interface NotifyKafkaMessageFabric {
    TaskResultKafkaMessage createMessage(MessageBody body);
}

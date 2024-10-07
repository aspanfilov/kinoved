package com.kinoved.filemanager.config.props;

public interface KafkaProps {
    String getFileTaskTopicName();

    int getFileTaskTopicPartitions();

    int getFileTaskTopicReplicas();

    String getFileNotifyTopicName();

    int getFileNotifyTopicPartitions();

    int getFileNotifyTopicReplicas();

    int getConsumerMaxPollRecords();

    int getConsumerMaxPollInterval();

    int getListenerPollTimeout();
}

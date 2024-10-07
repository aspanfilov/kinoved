package com.kinoved.config.props;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka")
@Getter
@RequiredArgsConstructor
public class AppProps implements KafkaProps {

    private final Topics topics;

    private final Consumer consumer;

    private final Listener listener;

    @Override
    public String getFileTaskTopicName() {
        return topics.getFileTask().getName();
    }

    @Override
    public int getFileTaskTopicPartitions() {
        return topics.getFileTask().getPartitions();
    }

    @Override
    public int getFileTaskTopicReplicas() {
        return topics.getFileTask().getReplicas();
    }

    @Override
    public String getFileNotifyTopicName() {
        return topics.getFileNotify().getName();
    }

    @Override
    public int getFileNotifyTopicPartitions() {
        return topics.getFileNotify().getPartitions();
    }

    @Override
    public int getFileNotifyTopicReplicas() {
        return topics.getFileNotify().getReplicas();
    }

    @Override
    public int getConsumerMaxPollRecords() {
        return consumer.getMaxPollRecords();
    }

    @Override
    public int getConsumerMaxPollInterval() {
        return consumer.getMaxPollInterval();
    }

    @Override
    public int getListenerPollTimeout() {
        return listener.getPollTimeout();
    }

    @Getter
    @RequiredArgsConstructor
    private static class Topics {
        private final Topic fileTask;

        private final Topic fileNotify;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Topic {
        private final String name;

        private final int partitions;

        private final int replicas;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Consumer {
        private final int maxPollRecords;

        private final int maxPollInterval;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Listener {
        private final int pollTimeout;
    }
}

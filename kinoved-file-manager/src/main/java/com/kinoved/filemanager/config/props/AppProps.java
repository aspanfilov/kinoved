package com.kinoved.filemanager.config.props;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Getter
@RequiredArgsConstructor
public class AppProps implements AppSettings, KafkaProps {

    private final Kafka kafka;

    private final Settings settings;

    @Override
    public String getSourceDirectory() {
        return settings.getSourceDirectory();
    }

    @Override
    public String getTargetDirectory() {
        return settings.getTargetDirectory();
    }

    @Override
    public String getUndefinedGenreFolder() {
        return settings.getUndefinedGenreFolder();
    }

    @Override
    public int getSourceDirectoryScanInterval() {
        return settings.getSourceDirectoryScanInterval();
    }

    @Override
    public String getFileTaskTopicName() {
        return kafka.getTopics().getFileTask().getName();
    }

    @Override
    public int getFileTaskTopicPartitions() {
        return kafka.getTopics().getFileTask().getPartitions();
    }

    @Override
    public int getFileTaskTopicReplicas() {
        return kafka.getTopics().getFileTask().getReplicas();
    }

    @Override
    public String getFileNotifyTopicName() {
        return kafka.getTopics().getFileNotify().getName();
    }

    @Override
    public int getFileNotifyTopicPartitions() {
        return kafka.getTopics().getFileNotify().getPartitions();
    }

    @Override
    public int getFileNotifyTopicReplicas() {
        return kafka.getTopics().getFileNotify().getReplicas();
    }

    @Override
    public int getConsumerMaxPollRecords() {
        return kafka.getConsumer().getMaxPollRecords();
    }

    @Override
    public int getConsumerMaxPollInterval() {
        return kafka.getConsumer().getMaxPollInterval();
    }

    @Override
    public int getListenerPollTimeout() {
        return kafka.getListener().getPollTimeout();
    }

    @Getter
    @RequiredArgsConstructor
    private static class Settings {
        private final String sourceDirectory;

        private final String targetDirectory;

        private final String undefinedGenreFolder;

        private final int sourceDirectoryScanInterval;
    }

    @Getter
    @RequiredArgsConstructor
    private static class Kafka {
        private final Topics topics;

        private final Consumer consumer;

        private final Listener listener;
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

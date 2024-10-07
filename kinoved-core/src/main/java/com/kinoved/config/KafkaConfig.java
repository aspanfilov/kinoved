package com.kinoved.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kinoved.common.kafka.messages.NotifyKafkaMessage;
import com.kinoved.common.kafka.messages.TaskKafkaMessage;
import com.kinoved.config.props.AppProps;
import com.kinoved.config.props.KafkaProps;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class KafkaConfig {

    @Autowired
    private KafkaProps kafkaProps;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = JacksonUtils.enhancedObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    @Bean
    public NewTopic fileTaskTopic() {
        return TopicBuilder
                .name(kafkaProps.getFileTaskTopicName())
                .partitions(kafkaProps.getFileTaskTopicPartitions())
                .replicas(kafkaProps.getFileTaskTopicReplicas())
                .build();
    }

    @Bean
    public ProducerFactory<String, TaskKafkaMessage> producerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {

        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, TaskKafkaMessage>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(mapper));

        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, TaskKafkaMessage> kafkaTemplate
            (ProducerFactory<String, TaskKafkaMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, NotifyKafkaMessage> consumerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProps.getConsumerMaxPollRecords());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaProps.getConsumerMaxPollInterval());

        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, NotifyKafkaMessage>(props);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(mapper));
        return kafkaConsumerFactory;
    }

    @Bean("listenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NotifyKafkaMessage>>
    listenerContainerFactory(ConsumerFactory<String, NotifyKafkaMessage> consumerFactory) {

        var factory = new ConcurrentKafkaListenerContainerFactory<String, NotifyKafkaMessage>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(kafkaProps.getListenerPollTimeout());

        var executor = new SimpleAsyncTaskExecutor("k-consumer-");
        executor.setConcurrencyLimit(10);
        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);

        return factory;
    }


}

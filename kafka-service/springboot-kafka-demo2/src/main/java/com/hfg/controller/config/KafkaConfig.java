package com.hfg.controller.config;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.internals.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zero
 * @Date: 2022/4/27 21:27
 * @Description:
 */
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic2() {
        return new NewTopic("demo2",3,(short)1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("demo3",5,(short)1);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "node1:9092");
        KafkaAdmin admin = new KafkaAdmin(configs);
        return admin;
    }

    @Bean
    @Autowired
    public KafkaTemplate<Integer, String> kafkaTemplate(ProducerFactory<Integer, String> producerFactory) {

        // 覆盖ProducerFactory原有设置
        Map<String, Object> configsOverride = new HashMap<>();
        configsOverride.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);

        KafkaTemplate<Integer, String> template = new KafkaTemplate<Integer, String>(
                producerFactory, true
        );
        return template;
    }
}

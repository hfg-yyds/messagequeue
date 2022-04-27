package com.hfg.confg;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zero
 * @Date: 2022/4/25 23:59
 * @Description:
 */
public class KafkaConfig {

    /**
     * 获取kafka生产者对象
     * @return  KafkaProducer
     */
    public static KafkaProducer<Integer, String> getKafkaProducer() {
        HashMap<String, Object> configs = new HashMap<>();
        //指定初始化连接用到的broker地址
        configs.put("bootstrap.servers","192.168.88.128:9092");
        //指定key的序列化类
        configs.put("key.serializer", IntegerSerializer.class);
        //指定value的序列化类
        configs.put("value.serializer", StringSerializer.class);
        configs.put("acks","all");
        configs.put("retries","3");
        KafkaProducer<Integer, String> kafkaProducer = new KafkaProducer<Integer, String>(configs);
        return kafkaProducer;
    }

    /**
     * 获取到kafka消费对象
     * @return KafkaConsumer
     */
    public static KafkaConsumer<String,String> getKafkaConsumer() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.88.128:9092");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // group.id很重要
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "mygrp1");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(configs);
        return consumer;
    }


}

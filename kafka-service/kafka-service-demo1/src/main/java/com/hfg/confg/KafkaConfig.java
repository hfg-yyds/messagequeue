package com.hfg.confg;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;

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
    public static KafkaConsumer<Integer,String> getKafkaConsumer() {

        return null;
    }


}

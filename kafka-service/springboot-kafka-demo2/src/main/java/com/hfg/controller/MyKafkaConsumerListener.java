package com.hfg.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Zero
 * @Date: 2022/4/27 21:17
 * @Description:
 */
@Component
public class MyKafkaConsumerListener {

    @KafkaListener(topics = "demo2")
    public void onMessage(ConsumerRecord<Integer,String> record) {
        System.out.println("================================================================");
        System.out.println(record.topic());
        System.out.println(record.key());
        System.out.println(record.partition());
        System.out.println(record.value());
        System.out.println("================================================================");
    }
}

package com.hfg.controller;

import com.hfg.confg.KafkaConfig;
import com.hfg.result.R;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Author: Zero
 * @Date: 2022/4/26 00:35
 * @Description:
 */
@RestController
@RequestMapping("/consumer")
public class KafkaConsumerController {

    @GetMapping("/demo1")
    public void comsumerMessage() {
        KafkaConsumer<String, String> consumer = KafkaConfig.getKafkaConsumer();
        consumer.subscribe(Arrays.asList("topic_1"));
        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(1_00);
            consumerRecords.forEach(System.out::println);
        }
    }
}

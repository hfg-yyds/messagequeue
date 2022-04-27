package com.hfg.controller;

import com.hfg.confg.KafkaConfig;
import com.hfg.result.R;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author: Zero
 * @Date: 2022/4/25 23:28
 * @Description:
 */
@RestController
@RequestMapping("/producer")
public class KafkaProducerController {

    @SneakyThrows
    @GetMapping("/demo1")
    @ApiOperation("kafka同步发送普通消息")
    public R demo1() {
        KafkaProducer<Integer, String> kafkaProducer = KafkaConfig.getKafkaProducer();
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("biz.name","producer.demo".getBytes()));
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<Integer, String>(
                "topic_1",
                0,
                0,
                "hell   o world!",
                headers
        );
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(producerRecord);
        RecordMetadata recordMetadata = recordMetadataFuture.get();
        kafkaProducer.close();
        return R.ok(recordMetadata.toString());
    }


    @SneakyThrows
    @GetMapping("/demo2")
    @ApiOperation("kafka异步发送普通消息")
    public R demo2() {
        KafkaProducer<Integer, String> kafkaProducer = KafkaConfig.getKafkaProducer();
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("biz.name","producer.demo".getBytes()));
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord<Integer, String>(
                "topic_1",
                0,
                0,
                "hello world!",
                headers
        );
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception==null) {
                    System.out.println(metadata.topic());
                    System.out.println(metadata.partition());
                    System.out.println(metadata.offset());
                } else {
                    System.out.println("发送失败");
                }
            }
        });
        kafkaProducer.close();
        return R.ok();
    }

}

package com.hfg.controller;

import com.hfg.result.R;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Zero
 * @Date: 2022/4/27 21:01
 * @Description:
 */
@RestController
@RequestMapping("/kafka")
public class KafkaSendMessage {
    @Resource
    private KafkaTemplate<Integer,String> kafkaTemplate;

    @SneakyThrows
    @GetMapping("/syncSend/{message}")
    public R syncSend(@PathVariable String message) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("demo2", message);
        SendResult<Integer, String> sendResult = future.get();
        RecordMetadata recordMetadata = sendResult.getRecordMetadata();
        System.out.println(recordMetadata.topic());
        System.out.println(recordMetadata.offset());
        System.out.println(recordMetadata.partition());
        System.out.println(recordMetadata.hasOffset());
        return R.ok();
    }

    @SneakyThrows
    @GetMapping("/asynSend/{message}")
    public R asynSend(@PathVariable String message) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send("demo2", 0, 1, message);
        //异步等待broker端返回发送结果
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("失败了");
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                System.out.println("成功了");
                RecordMetadata recordMetadata = result.getRecordMetadata();
                System.out.println(recordMetadata.topic());
            }
        });
        return R.ok();
    }


}

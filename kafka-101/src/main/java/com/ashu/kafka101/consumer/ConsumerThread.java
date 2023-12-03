package com.ashu.kafka101.consumer;

import com.ashu.kafka101.config.ConsumerBean;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

@Component
public class ConsumerThread implements Runnable {

    @Autowired
    ConsumerBean consumerBean;

    public ConsumerThread() {
        this.consumerBean = new ConsumerBean();
    }

    @Override
    public void run() {
        try {
            consumeMessages();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void consumeMessages() throws InterruptedException {
        Consumer<String, String> consumer = consumerBean.getConsumer();
        if (consumer != null) {
            consumer.subscribe(Collections.singletonList("kafka.learning.orders"));

            while (true) {
                final ConsumerRecords<String, String> consumerRecords =
                        consumer.poll(Duration.ofSeconds(1));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    // process the record
                    System.out.println("***** FROM CONSUMER *****");
                    System.out.println(record.key() + " -> " + record.value());
                    System.out.println("***** END OF CONSUMER *****");
                }
            }
        } else {
            System.err.println("Consumer is null. Please check the ConsumerBean configuration.");
        }
    }
}

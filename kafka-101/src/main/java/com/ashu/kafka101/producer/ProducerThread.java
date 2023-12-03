package com.ashu.kafka101.producer;

import com.ashu.kafka101.config.ProducerBean;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerThread implements Runnable {

    @Autowired
    ProducerBean producerBean;

    public ProducerThread() {
        this.producerBean = new ProducerBean();
    }

    @Override
    public void run() {
        try {
            produceMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void produceMessages() throws InterruptedException {
        Producer<String, String> producer = producerBean.getProducer();

        String topic = "kafka.learning.orders";

        String msgKey;
        String msgValue;
        String msgTemplate = "This is message %s for key %s";

        int startingKey = 1001;
        int startingMsgIndex = 1;

        while (true) {
            msgKey = Integer.toString(startingKey);
            startingKey++;

            msgValue = String.format(msgTemplate, startingKey, startingMsgIndex);
            startingMsgIndex++;

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, msgKey, msgValue);
            System.out.println("***** FROM PRODUCER *****");
            System.out.println("Produced record " + msgKey + " " + msgValue);
            System.out.println("***** END OF PRODUCER *****");

            producer.send(record);

            Thread.sleep(1000);
        }
    }
}

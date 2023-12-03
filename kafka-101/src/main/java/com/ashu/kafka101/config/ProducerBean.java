package com.ashu.kafka101.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class ProducerBean {

    private Producer<String, String> producer;

    @PostConstruct
    public void initProducer() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        producer = new KafkaProducer<>(producerProps);
    }

    @Bean
    public Producer<String, String> getProducer() {
        return producer;
    }


//    Properties producerProps = new Properties();
//
//    @Bean
//    public Producer<String,String> getProducer(){
//        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer .class);
//        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//
//        Producer<String, String> producer = null;
//
//        try{
//             producer = new KafkaProducer<>(producerProps);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return producer;
//
//    }

}

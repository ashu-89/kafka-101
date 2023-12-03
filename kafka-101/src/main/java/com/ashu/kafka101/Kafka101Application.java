package com.ashu.kafka101;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class Kafka101Application {

	public static void main(String[] args) {
		SpringApplication.run(Kafka101Application.class, args);

		Properties producerProps = new Properties();
		producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		String topic = "kafka.learning.orders";
		Map<String, String> message = new HashMap<>();
		message.put("7007", "msg 7007");
		message.put("7008", "msg 7008");

		//Producer<String, String> producer = new KafkaProducer<>(props);

		try (Producer<String, String> producer = new KafkaProducer<>(producerProps)) {


			for (Map.Entry<String, String> entry : message.entrySet()) {
				producer.send(new ProducerRecord<>(topic, entry.getKey(), entry.getValue()), (metadata, exception) -> {
					if (exception != null) {
						System.out.println("Trouble producing" + exception.getMessage());
					} else {
						System.out.println("***** FROM PRODUCER *****");
						System.out.println("Produced record " + entry.getKey() + " -> " + entry.getValue() + " at offset " + metadata.offset() +
								" to topic " + metadata.topic());
						System.out.println("***** END OF PRODUCER *****");
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		//for(int i =0; i< 10; i++){
//
//			try (Producer<String, String> producer = new KafkaProducer<>(props)) {
//
//				producer.send(new ProducerRecord<>(topic, message.toString()), (metadata, exception) -> {
//					if (exception != null) {
//						//logger.error("Trouble producing", exception);
//						System.out.println("Trouble producing" + exception.getMessage());
//					} else {
//						//logger.debug("Produced record (%s) at offset %d to topic %s %n", message,
//						//		metadata.offset(), metadata.topic());
//
//						System.out.println("***** FROM PRODUCER *****");
//						System.out.println("Produced record " + message + "at offset " + metadata.offset() +
//								"to topic " + metadata.topic() );
//						System.out.println("***** END OF PRODUCER *****");
//					}
//				});
//			}
//
//		//}


		Properties consumerProps = new Properties();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "ashuz-consumers-007");
		//consumerProps.put("key.deserializer", StringSerializer.class);
		//consumerProps.put("value.deserializer", StringSerializer.class);
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		boolean keepConsuming = true;

		try (Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps)) {
			consumer.subscribe(Collections.singletonList(topic));

			while (keepConsuming) {
				final ConsumerRecords<String, String> consumerRecords =
						consumer.poll(Duration.ofSeconds(1));
				for (ConsumerRecord<String, String> record : consumerRecords) {

					//process the record
					System.out.println("***** FROM CONSUMER *****");
					System.out.println(record.key() + " -> " + record.value());
					System.out.println("***** END OF CONSUMER *****");
				}
			}
		}

	}

}

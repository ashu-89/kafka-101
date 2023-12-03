package com.ashu.kafka101;

import com.ashu.kafka101.consumer.ConsumerThread;
import com.ashu.kafka101.producer.ProducerThread;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync(proxyTargetClass = true) // Add this line to force CGLib-based proxies
public class Kafka101Application {

	@Autowired
	private ProducerThread producerThread;

	@Autowired
	private ConsumerThread consumerThread;

	public static void main(String[] args) {
		SpringApplication.run(Kafka101Application.class, args);
	}

	// Add this method to start the threads after the application context is initialized
	@PostConstruct
	public void init() {
		// Create and start producer thread
		Thread producerThreadWrapper = new Thread(producerThread);
		producerThreadWrapper.start();

		// Create and start consumer thread
		Thread consumerThreadWrapper = new Thread(consumerThread);
		consumerThreadWrapper.start();
	}
}

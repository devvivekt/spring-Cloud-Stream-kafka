package com.learning.kafka.kafkaproducer.json;

import com.learning.kafka.kafkaproducer.json.services.KafkaProducerService;
import com.learning.kafka.kafkaproducer.json.services.datagenerator.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaproducerApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaproducerApplication.class, args);
	}

	@Autowired
	KafkaProducerService producer;

	@Autowired
	InvoiceGenerator generator;

	@Value("${application.configs.invoice.count}")
	private int messageCount;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		for( int i = 0; i< messageCount; i++){
			producer.sendMessage(generator.getNextInvoice());
			Thread.sleep(1000);
		}

	}
}

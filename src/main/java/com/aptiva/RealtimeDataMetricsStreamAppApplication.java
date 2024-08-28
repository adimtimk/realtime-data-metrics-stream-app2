package com.aptiva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class RealtimeDataMetricsStreamAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeDataMetricsStreamAppApplication.class, args);
	}

}

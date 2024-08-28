package com.aptiva.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.aptiva.topology.SalesTopology;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	NewTopic salesTopics() {
		return TopicBuilder.name(SalesTopology.salesTopic).partitions(1).replicas(1).build();
	}
	
	@Bean
	NewTopic salesTopicsDummy() {
		return TopicBuilder.name(SalesTopology.salesTopicD).partitions(1).replicas(1).build();
	}
 
   
	@Bean
	ObjectMapper objectMapperCustom() {
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	
	
}

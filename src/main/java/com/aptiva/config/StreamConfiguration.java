package com.aptiva.config;


import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.streams.RecoveringDeserializationExceptionHandler;

import com.aptiva.exception.StreamsProcessorCustomErrorHandler;
import com.aptiva.exception.StreamsProductionsExceptionHandler;

import lombok.extern.log4j.Log4j2;
@Configuration
@Log4j2
public class StreamConfiguration {

	@Autowired
	KafkaProperties kafkaProperties;
	
	@Autowired
	DefaultSslBundleRegistry defaultssl;
	
	@Bean(name=KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	KafkaStreamsConfiguration kafkaStreamsConfiguration() {
		
		var kafkaStreamprops =	kafkaProperties.buildStreamsProperties(defaultssl);
		
		kafkaStreamprops.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, RecoveringDeserializationExceptionHandler.class);
		kafkaStreamprops.put(RecoveringDeserializationExceptionHandler.KSTREAM_DESERIALIZATION_RECOVERER, recoverer());	
		kafkaStreamprops.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsProductionsExceptionHandler.class);
		//kafkaStreamprops.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		//kafkaStreamprops.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, new JsonSerde<>(Sales.class));
		return new KafkaStreamsConfiguration(kafkaStreamprops);
		
	}
	@Bean
	StreamsBuilderFactoryBeanConfigurer beanConfigurerFactoryBeanConfigurer()
	{
		//u can overrite default configurations 
		return  fbc -> {
			fbc.setStreamsUncaughtExceptionHandler(new StreamsProcessorCustomErrorHandler());
		};
	}

	private ConsumerRecordRecoverer recoverer() {
		// TODO Auto-generated method stub
		return (cs,e)-> {
			log.info("Exception is {} and ,Failed Record is {}",e.getMessage(),cs);
		};
	}
}

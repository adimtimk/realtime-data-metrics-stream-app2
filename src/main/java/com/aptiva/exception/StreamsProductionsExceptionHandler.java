package com.aptiva.exception;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
@Component
public class StreamsProductionsExceptionHandler implements ProductionExceptionHandler {
    @Override
    public ProductionExceptionHandlerResponse handle(ProducerRecord<byte[], byte[]> record, Exception exception) {
        log.error("Exception in handle : {}  and the record is : {} ", exception.getMessage(), record, exception);
        return ProductionExceptionHandlerResponse.CONTINUE;
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
package com.aptiva.exception;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
@Component
public class StreamsDeserializationErrorHandler implements DeserializationExceptionHandler {
    int errorCounter = 0;

    @Override
    public DeserializationHandlerResponse handle(ProcessorContext context, ConsumerRecord<byte[], byte[]> record, Exception exception) {

        log.error("Exception is : {} and the Kafka Record is : {} " , exception.getMessage(), record,  exception);
        log.error("errorCounter is : {} " , errorCounter);
        if(errorCounter < 10){
            errorCounter++;
            return DeserializationHandlerResponse.CONTINUE;
        }
        return DeserializationHandlerResponse.FAIL;
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}

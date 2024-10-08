package com.aptiva.exception;


import org.apache.kafka.streams.errors.StreamsException;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class StreamsProcessorCustomErrorHandler implements StreamsUncaughtExceptionHandler {
    @Override
    public StreamThreadExceptionResponse handle(Throwable exception) {
        log.error("Exception in the Application : {} ",exception.getMessage(), exception);
        if(exception instanceof StreamsException){
            var cause = exception.getCause();
            if(cause.getMessage().equals("Transient Error")){
                //return StreamThreadExceptionResponse.REPLACE_THREAD;
                return StreamThreadExceptionResponse.SHUTDOWN_CLIENT;
            }
        }
        log.error("Shutdown the client");
        //return StreamThreadExceptionResponse.SHUTDOWN_CLIENT;
        return StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
    }
}
//package com.aptiva.producer;
//
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import com.aptiva.domain.Sales;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class SalesMockDataProducer {
//
//    static String GREETINGS = "greetings";
//
//    public static void main(String[] args) {
//        ObjectMapper objectMapper = new ObjectMapper()
//                .registerModule(new JavaTimeModule())
//                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//
//        englishGreetings(objectMapper);
//        spanishGreetings(objectMapper);
//
//    }
//
//    private static void spanishGreetings(ObjectMapper objectMapper) {
//        var spanishGreetings = List.of(
//                new Sales(101,LocalDateTime.now(),100.12),
//                new Sales(102,LocalDateTime.now().plusMinutes(2),101.12),
//                new Sales(103,LocalDateTime.now().plusMinutes(3),102.12))
//        );
//        spanishGreetings
//                .forEach(greeting -> {
//                    try {
//                        var greetingJSON = objectMapper.writeValueAsString(greeting);
//                        var recordMetaData = publishMessageSync(GREETINGS, null, greetingJSON);
//                        log.info("Published the alphabet message : {} ", recordMetaData);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//    }
//
//    private static void englishGreetings(ObjectMapper objectMapper) {
//        var spanishGreetings = List.of(
//        	     new Sales(101,LocalDateTime.now(),100.12),
//                 new Sales(102,LocalDateTime.now().plusMinutes(2),101.12),
//                 new Sales(103,LocalDateTime.now().plusMinutes(3),102.12))
//        );
//        spanishGreetings
//                .forEach(greeting -> {
//                    try {
//                        var greetingJSON = objectMapper.writeValueAsString(greeting);
//                        var recordMetaData = publishMessageSync(GREETINGS, null, greetingJSON);
//                        log.info("Published the alphabet message : {} ", recordMetaData);
//                    } catch (JsonProcessingException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//    }
//
//}
//

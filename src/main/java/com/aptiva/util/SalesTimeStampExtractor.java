//package com.aptiva.util;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.streams.processor.TimestampExtractor;
//import org.springframework.stereotype.Component;
//
//import com.aptiva.domain.Sales;
//
//import lombok.extern.log4j.Log4j2;
//@Component
//@Log4j2
//public class SalesTimeStampExtractor implements TimestampExtractor {
//
//	@Override
//	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
//		// TODO Auto-generated method stub
//		
//		var sales = (Sales)record.value();
//		if(sales !=null && sales.getSalesDateTxn() !=null) {
//			var ts = sales.getSalesDateTxn().toLocalDateTime();
//			log.info("ts in extractor {}" , ts);
//			return convertToInstantFromUTC(ts);
//		}
//		
//		log.info("partitionTime {}" , partitionTime);
//		return partitionTime;
//	}
//
//	private long convertToInstantFromUTC(Object ts) {
//		// TODO Auto-generated method stub
//		
//		return ts.toInstant(ZoneOffset.of("-05:30")).toEpochMilli();
//	}
//
//}

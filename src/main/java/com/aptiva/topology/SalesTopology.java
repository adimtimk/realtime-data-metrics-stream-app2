package com.aptiva.topology;

import java.time.LocalDateTime;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import com.aptiva.model.AggregateSalesPerMonthStoreCount;
import com.aptiva.model.Sales;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.KeyValue;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class SalesTopology {
	public static String salesTopic = "salestopicnew2";
	public static String salesTopicD = "stopicdummynew";
	@Autowired
	@Qualifier("objectMapperCustom")
	private ObjectMapper objectMapper;
	
	@Autowired
	public void process(StreamsBuilder builder) {
		//countBySalesMonth(builder);
		var s = builder.stream(salesTopic, Consumed.with(Serdes.String(),new JsonSerde<>(Sales.class,objectMapper)) );

		//initialize step 1
		Initializer<AggregateSalesPerMonthStoreCount> salesCountAmount = AggregateSalesPerMonthStoreCount::new; 
		//create Aggregator step 2
		Aggregator<Integer , Sales, AggregateSalesPerMonthStoreCount> aggregatorSales = (key, value, aggregate) -> aggregate.updateEvents(key,value);

		//
		var keychanged =  s.map((k,v)-> org.apache.kafka.streams.KeyValue.pair(v.getSalesDateTxn().getMonth().getValue(),v));
		
		
		var ktable = keychanged.groupByKey(Grouped.with(Serdes.Integer(), new JsonSerde<>(Sales.class,objectMapper)));
		
		
		
		var aggregated = ktable.aggregate(AggregateSalesPerMonthStoreCount::new, aggregatorSales,Materialized.<Integer,AggregateSalesPerMonthStoreCount,KeyValueStore<Bytes, byte[]>>as("sales-amount-by-month")
				.withKeySerde(Serdes.Integer())
				.withValueSerde(new JsonSerde<>(AggregateSalesPerMonthStoreCount.class,objectMapper)));
		
		aggregated.toStream().print(Printed.<Integer, AggregateSalesPerMonthStoreCount>toSysOut().withLabel("aggregated ktable updated stream"));

	
	}

	private void countBySalesMonth(StreamsBuilder builder) {
		var s = builder.stream(salesTopic, Consumed.with(Serdes.String(),new JsonSerde<>(Sales.class,objectMapper)) );
		
		s.print(Printed.<String, Sales>toSysOut().withLabel("Sales  stream"));
		
		
		var keychanged = s.map((k,v)->org.apache.kafka.streams.KeyValue.pair(v.getSalesDateTxn().getMonth().getValue(), v));
		
		
		
		
		var ktable = keychanged.groupByKey(Grouped.with(Serdes.Integer(), new JsonSerde<>(Sales.class,objectMapper))).count();
		
		ktable.toStream().print(Printed.<Integer, Long>toSysOut().withLabel("sales ktable updated stream"));
	}

}

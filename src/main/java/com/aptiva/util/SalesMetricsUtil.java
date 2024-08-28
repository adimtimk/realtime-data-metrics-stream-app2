package com.aptiva.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreType;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

import com.aptiva.model.AggregateSalesPerMonthStoreCount;

import lombok.extern.log4j.Log4j2;
@Component
@Log4j2
public class SalesMetricsUtil {

	@Autowired
	private StreamsBuilderFactoryBean streamsBuilderFactoryBean;
	
	
	public List<KeyValue<Integer, AggregateSalesPerMonthStoreCount>> getAllMonthsAggData() {
		var storeMap = this.intializeStore();
		List<KeyValue<Integer, AggregateSalesPerMonthStoreCount>> list = new ArrayList<>();
		var kviterator = storeMap.all();
		
		while(kviterator.hasNext()) {
			list.add(kviterator.next());
		}
		return list;
	}
	
	
	public ReadOnlyKeyValueStore<Integer, AggregateSalesPerMonthStoreCount> intializeStore(){
		KafkaStreams kstreams = streamsBuilderFactoryBean.getKafkaStreams();
		
		ReadOnlyKeyValueStore<Integer, AggregateSalesPerMonthStoreCount> store = kstreams.store(StoreQueryParameters.fromNameAndType("sales-amount-by-month", QueryableStoreTypes.keyValueStore()));
	
		return store;
	
	}

}

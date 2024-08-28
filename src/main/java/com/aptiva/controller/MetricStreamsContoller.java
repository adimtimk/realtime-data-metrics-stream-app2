package com.aptiva.controller;

import java.util.List;

import org.apache.kafka.streams.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptiva.model.AggregateSalesPerMonthStoreCount;
import com.aptiva.util.SalesMetricsUtil;

@RestController
@RequestMapping("/vi")
public class MetricStreamsContoller {

	@Autowired
	private SalesMetricsUtil smutil;
	
	
	@GetMapping("/alls")
	public List<KeyValue<Integer, AggregateSalesPerMonthStoreCount>> getAllMonthsData(){
		
		return smutil.getAllMonthsAggData();
		
	}
}

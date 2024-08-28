package com.aptiva.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Log4j2
public class AggregateSalesPerMonthStoreCount {
	private Integer salesMonth;
	private Integer runningSalesCount;
	private Double runningSalesAmount;
	

	public AggregateSalesPerMonthStoreCount updateEvents(Integer key, Sales value) {
		var newSalesCount = this.runningSalesCount + 1;
		log.info("Sales {}-am{}-cm{}",value,this.runningSalesAmount,this.runningSalesCount);

		var newSalesAmount = this.runningSalesAmount + value.getSalesAmount();
		log.info("running count {} and running sales {} ",newSalesAmount,newSalesCount);
		return new AggregateSalesPerMonthStoreCount(key, newSalesCount, newSalesAmount);
	}



}

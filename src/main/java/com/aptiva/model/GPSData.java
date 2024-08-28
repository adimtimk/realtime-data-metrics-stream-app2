package com.aptiva.model;





import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("GPS_TXN")
public class GPSData {
	@PrimaryKey
	private Integer gpsTxnID;
	private Integer customerId;
	private Integer carID;
	private Integer officeID;
	private Integer agentID;
	private Timestamp gtimeStamp;
	private String carMovementStatus;
	private Double currLongitude;
	private Double currLatitude;
	private String currArea;
	private Double currKilometers;
}

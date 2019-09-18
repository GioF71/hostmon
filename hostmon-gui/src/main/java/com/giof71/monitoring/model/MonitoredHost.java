package com.giof71.monitoring.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { 
	@UniqueConstraint(columnNames = "friendlyName"), 
	@UniqueConstraint(columnNames = "address") })
public class MonitoredHost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String friendlyName;
	
	private String address;
	
	private Calendar creationTimestamp = Calendar.getInstance();
	
	private Calendar updateTimestamp = creationTimestamp;
	
}

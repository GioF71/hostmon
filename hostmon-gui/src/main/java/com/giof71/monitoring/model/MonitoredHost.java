package com.giof71.monitoring.model;

import java.util.Calendar;

import javax.persistence.Column;
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
	
	@Column(nullable = false)
	private String friendlyName;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private Calendar creationTimestamp = Calendar.getInstance();
	
	@Column(nullable = false)
	private Calendar updateTimestamp = creationTimestamp;
}

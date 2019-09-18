package com.giof71.monitoring.route.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoredHostView {
	
	private Long id;

	private String friendlyName;
	
	private String address;
	
	private String creationTimestamp;
	
	private String updateTimestamp;
}

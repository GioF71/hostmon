package com.giof71.monitoring.service;

import java.util.List;
import java.util.Optional;

import com.giof71.monitoring.model.MonitoredHost;

public interface HostService {
	MonitoredHost addHost(String friendlyName, String address);
	List<MonitoredHost> findAll();
	long getHostCount();
	void remove(Long id);
	Optional<MonitoredHost> getHost(Long hostId);
	MonitoredHost save(MonitoredHost monitoredHost);
}


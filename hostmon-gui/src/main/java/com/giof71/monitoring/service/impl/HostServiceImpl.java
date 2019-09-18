package com.giof71.monitoring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.repository.HostRepository;
import com.giof71.monitoring.service.HostService;

@Component
public class HostServiceImpl implements HostService {
	
	@Autowired
	private HostRepository hostRepository;

	@Override
	public long getHostCount() {
		return hostRepository.count();
	}

	@Transactional
	@Override
	public MonitoredHost addHost(String friendlyName, String address) {
		MonitoredHost host = new MonitoredHost();
		host.setFriendlyName(friendlyName);
		host.setAddress(address);
		hostRepository.save(host);
		return host;
	}

	@Override
	public List<MonitoredHost> findAll() {
		List<MonitoredHost> list = new ArrayList<>();
		hostRepository.findAll().forEach(list::add);
		return list;
	}

	@Transactional
	@Override
	public void remove(Long id) {
		hostRepository.deleteById(id);
	}

	@Override
	public Optional<MonitoredHost> getHost(Long hostId) {
		return hostRepository.findById(hostId);
	}

	@Transactional
	@Override
	public void save(MonitoredHost monitoredHost) {
		hostRepository.save(monitoredHost);
	}
}

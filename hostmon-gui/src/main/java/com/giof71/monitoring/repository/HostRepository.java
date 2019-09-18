package com.giof71.monitoring.repository;

import org.springframework.data.repository.CrudRepository;

import com.giof71.monitoring.model.MonitoredHost;

public interface HostRepository extends CrudRepository<MonitoredHost, Long> {

}

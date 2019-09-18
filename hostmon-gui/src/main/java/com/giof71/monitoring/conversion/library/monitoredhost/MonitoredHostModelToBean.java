package com.giof71.monitoring.conversion.library.monitoredhost;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.giof71.monitoring.bean.MonitoredHostView;
import com.giof71.monitoring.conversion.library.abs.AbsConverterHelper;
import com.giof71.monitoring.model.MonitoredHost;

@Component
public class MonitoredHostModelToBean extends AbsConverterHelper<MonitoredHost, MonitoredHostView> {

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	protected Class<MonitoredHost> getTypedFrom() {
		return MonitoredHost.class;
	}

	@Override
	protected Class<MonitoredHostView> getTypedTo() {
		return MonitoredHostView.class;
	}

	@Override
	protected MonitoredHostView doConvert(MonitoredHost monitoredHost) {
		MonitoredHostView converted = new MonitoredHostView();
		converted.setId(monitoredHost.getId());
		converted.setFriendlyName(monitoredHost.getFriendlyName());
		converted.setAddress(monitoredHost.getAddress());
		converted.setCreationTimestamp(convertCalendar(monitoredHost.getCreationTimestamp()));
		converted.setUpdateTimestamp(convertCalendar(monitoredHost.getUpdateTimestamp()));
		return converted;
	}
	
	private String convertCalendar(Calendar calendar) {
		return Optional.ofNullable(calendar)
			.map(Calendar::getTime)
			.map(c -> dateFormat.format(c))
			.orElse(null);
	}
}

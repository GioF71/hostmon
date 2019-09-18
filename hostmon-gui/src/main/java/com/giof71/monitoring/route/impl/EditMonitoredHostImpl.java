package com.giof71.monitoring.route.impl;

import javax.annotation.PostConstruct;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.EditMonitoredHost;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;

//@Route(value = "edithost")
//@Component
//@UIScope
public class EditMonitoredHostImpl extends VerticalLayout implements EditMonitoredHost, HasUrlParameter<Long> {
	
	private static final long serialVersionUID = 8130239827721092587L;

	private Long hostId;
	private Label editHost;

	@PostConstruct
	private void postConstruct() {
		editHost = new Label("Edit host");
		add(editHost);
	}

	@Override
	public void setParameter(BeforeEvent event, Long parameter) {
		this.hostId = parameter;
		// TODO Auto-generated method stub
		//QueryParameters qp = event.getLocation().getQueryParameters();
		editHost.setText(String.format("Editing %s %d", MonitoredHost.class.getSimpleName(), hostId));
	}
}

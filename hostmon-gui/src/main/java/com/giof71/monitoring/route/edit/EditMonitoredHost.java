package com.giof71.monitoring.route.edit;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.start.StartPage;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "edithost")
@Component
@UIScope
public class EditMonitoredHost extends AddEditMonitoredHost implements HasUrlParameter<Long> {

	private static final long serialVersionUID = -2252010460912666187L;

	private Long hostId;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		//setErrorMessage("");
	}

	@Override
	protected ComponentEventListener<ClickEvent<Button>> getExecuteActionClickListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {
			
			private static final long serialVersionUID = 3308376329602494624L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				MonitoredHost monitoredHost = getHostService().getHost(hostId).orElse(null);
				if (monitoredHost != null) {
					monitoredHost.setFriendlyName(getFriendlyName().getValue());
					monitoredHost.setAddress(getAddress().getValue());
					monitoredHost.setUpdateTimestamp(Calendar.getInstance());
					try {
						getHostService().save(monitoredHost);
						UI.getCurrent().navigate(StartPage.class);
					} catch (Exception exc) {
						setErrorMessage(String.format("Cannot save %s due to %s [%s]", 
							MonitoredHost.class.getSimpleName(),
							Exception.class.getSimpleName(),
							exc.getClass().getSimpleName()));
					}
				} else {
					// so?
					String errorMessage = String.format("Cannot find a %s with id = %d", MonitoredHost.class.getSimpleName(), hostId);
					setErrorMessage(errorMessage);
				}
			}
		};
	}
	
	@Override
	public void setParameter(BeforeEvent event, Long parameter) {
		this.hostId = parameter;
		refresh();
	}

	private void refresh() {
		setErrorMessage("");
		getEditHostLabel().setText(String.format("Editing %s %d", MonitoredHost.class.getSimpleName(), hostId));
		MonitoredHost monitoredHost = getHostService().getHost(hostId).orElse(null);
		if (monitoredHost != null) {
			getFriendlyName().setValue(monitoredHost.getFriendlyName());
			getAddress().setValue(monitoredHost.getAddress());
		} else {
			String errorMessage = String.format("Cannot find a %s with id = %d", MonitoredHost.class.getSimpleName(), hostId);
			setErrorMessage(errorMessage);
		}
	}
}
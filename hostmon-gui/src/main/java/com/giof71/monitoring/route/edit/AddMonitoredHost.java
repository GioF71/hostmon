package com.giof71.monitoring.route.edit;

import org.springframework.stereotype.Component;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.start.StartPage;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "addhost")
@Component
@UIScope
public class AddMonitoredHost extends AddEditMonitoredHost {

	private static final long serialVersionUID = -7801912693497319347L;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		getFriendlyName().setValue("");
		getAddress().setValue("");
	}

	@Override
	protected ComponentEventListener<ClickEvent<Button>> getExecuteActionClickListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {
			
			private static final long serialVersionUID = -493473661498089914L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				MonitoredHost monitoredHost = new MonitoredHost();
				monitoredHost.setFriendlyName(getFriendlyName().getValue());
				monitoredHost.setAddress(getAddress().getValue());
				getHostService().save(monitoredHost);
				getStartPage().refresh();
				UI.getCurrent().navigate(StartPage.class);
			}
		};
	}
}

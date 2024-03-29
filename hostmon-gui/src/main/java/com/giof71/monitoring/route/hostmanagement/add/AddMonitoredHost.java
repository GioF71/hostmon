package com.giof71.monitoring.route.hostmanagement.add;

import org.springframework.stereotype.Component;

import com.giof71.monitoring.editing.Action;
import com.giof71.monitoring.editing.EditorAction;
import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.hostmanagement.MonitoredHostEditor;
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
@EditorAction(action = Action.ADD, buttonText = "Add New Host")
public class AddMonitoredHost extends MonitoredHostEditor {

	private static final long serialVersionUID = -7801912693497319347L;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		getFriendlyName().setValue("");
		getAddress().setValue("");
		resetErrors();
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
				try {
					getHostService().save(monitoredHost);
					UI.getCurrent().navigate(StartPage.class);
				} catch (Exception exc) {
					addErrorMessage(String.format("Cannot save %s due to %s [%s]", 
						MonitoredHost.class.getSimpleName(),
						Exception.class.getSimpleName(),
						exc.getClass().getSimpleName()));
				}
			}
		};
	}
}

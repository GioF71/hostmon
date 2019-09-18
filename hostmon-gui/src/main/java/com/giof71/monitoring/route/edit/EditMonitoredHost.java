package com.giof71.monitoring.route.edit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.start.StartPage;
import com.giof71.monitoring.service.HostService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "edithost")
@Component
@UIScope
public class EditMonitoredHost extends VerticalLayout implements HasUrlParameter<Long> {
	
	private static final long serialVersionUID = 8130239827721092587L;
	
	@Autowired
	private StartPage startPage;
	
	@Autowired
	private HostService hostService;

	private Long hostId;
	private Label editHost;
	
	private TextField tfFriendlyName;

	@PostConstruct
	private void postConstruct() {
		editHost = new Label("Edit host");
		add(editHost);
		
		VerticalLayout layout = new VerticalLayout();
		
		tfFriendlyName = new TextField("Friendly Name");
		layout.add(tfFriendlyName);
		add(layout);
		
		HorizontalLayout bottomButtons = new HorizontalLayout();
		Button updateButton = new Button("Update", updateClickListener);
		bottomButtons.add(updateButton);
		
		Button cancelButton = new Button("Return to Host List", x -> UI.getCurrent().navigate(StartPage.class));
		bottomButtons.add(cancelButton);
		add(bottomButtons);
	}
	
	private final ComponentEventListener<ClickEvent<Button>> updateClickListener = new ComponentEventListener<ClickEvent<Button>>() {
		
		private static final long serialVersionUID = -1115701644460525692L;

		@Override
		public void onComponentEvent(ClickEvent<Button> event) {
			MonitoredHost monitoredHost = hostService.getHost(hostId).orElse(null);
			if (monitoredHost != null) {
				monitoredHost.setFriendlyName(tfFriendlyName.getValue());
				hostService.save(monitoredHost);
				startPage.refresh();
				UI.getCurrent().navigate(StartPage.class);
			} else {
				// so?
			}
		}
	};

	@Override
	public void setParameter(BeforeEvent event, Long parameter) {
		this.hostId = parameter;
		// TODO Auto-generated method stub
		//QueryParameters qp = event.getLocation().getQueryParameters();
		refresh();
	}

	private void refresh() {
		editHost.setText(String.format("Editing %s %d", MonitoredHost.class.getSimpleName(), hostId));
		MonitoredHost monitoredHost = hostService.getHost(hostId).orElse(null);
		if (monitoredHost != null) {
			tfFriendlyName.setValue(monitoredHost.getFriendlyName());
		} else {
			// not found...
		}
	}
}

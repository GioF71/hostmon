package com.giof71.monitoring.route.hostmanagement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.giof71.monitoring.editing.EditorDefinition;
import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.HostManagement;
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

@EditorDefinition(modelClass = MonitoredHost.class, requiredQualifier = HostManagement.class)
public abstract class AddEditMonitoredHost extends VerticalLayout {
	
	private static final long serialVersionUID = 36897351304630125L;
	
	@Autowired
	private HostService hostService;
	
	private Label editHost;
	private TextField tfFriendlyName;
	private TextField tfAddress;
	
	private Label errorMessage;
	
	protected abstract ComponentEventListener<ClickEvent<Button>> getExecuteActionClickListener();
	
	protected HostService getHostService() {
		return hostService;
	}
	
	protected TextField getFriendlyName() {
		return tfFriendlyName;
	}
	
	protected TextField getAddress() {
		return tfAddress;
	}
	
	protected Label getEditHostLabel() {
		return editHost;
	}
	
	@PostConstruct
	private void postConstruct() {
		editHost = new Label("Edit host");
		add(editHost);
		
		VerticalLayout layout = createDataLayout();
		add(layout);
		
		HorizontalLayout messageLayout = new HorizontalLayout();
		errorMessage = new Label();
		messageLayout.add(errorMessage);
		messageLayout.setWidthFull();
		add(messageLayout);
		
		HorizontalLayout bottomLayout = createBottomLayout();
		add(bottomLayout);
	}

	private VerticalLayout createDataLayout() {
		VerticalLayout layout = new VerticalLayout();
		
		tfFriendlyName = new TextField("Friendly Name");
		tfFriendlyName.setRequired(true);
		layout.add(tfFriendlyName);
	
		tfAddress = new TextField("Address");
		tfAddress.setRequired(true);
		layout.add(tfAddress);
		return layout;
	}

	private HorizontalLayout createBottomLayout() {
		HorizontalLayout bottomButtons = new HorizontalLayout();
		Button updateButton = new Button("Execute", getExecuteActionClickListener());
		bottomButtons.add(updateButton);
		
		Button cancelButton = new Button("Return to Host List", x -> UI.getCurrent().navigate(StartPage.class));
		bottomButtons.add(cancelButton);
		return bottomButtons;
	}
	
	protected void setErrorMessage(String message) {
		errorMessage.setText(message);
	}
}
package com.giof71.monitoring.route.hostmanagement;

import com.giof71.monitoring.editing.EditingLayout;
import com.giof71.monitoring.editing.impl.SimpleEditingLayout;
import com.giof71.monitoring.editing.impl.SimpleEditingLayoutCreator;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public abstract class HostAbsLayoutCreator extends SimpleEditingLayoutCreator {

	@Override
	public EditingLayout create() {
		SimpleEditingLayout.Builder builder = SimpleEditingLayout.builder();
		VerticalLayout mainEditingLayout = new VerticalLayout();
		Label editHost = new Label("Edit host");
		mainEditingLayout.add(editHost);
		
		VerticalLayout layout = createDataLayout(builder);
		mainEditingLayout.add(layout);
		
		HorizontalLayout messageLayout = new HorizontalLayout();
		Label errorMessage = new Label();
		messageLayout.add(errorMessage);
		messageLayout.setWidthFull();
		mainEditingLayout.add(messageLayout);
		
		return builder.layoutComponent(mainEditingLayout).build();
	}

	private VerticalLayout createDataLayout(SimpleEditingLayout.Builder builder) {
		VerticalLayout layout = new VerticalLayout();
		
		TextField tfFriendlyName = new TextField("Friendly Name");
		tfFriendlyName.setRequired(true);
		layout.add(tfFriendlyName);
		builder.addComponent("FRIENDLY_NAME", tfFriendlyName);
	
		TextField tfAddress = new TextField("Address");
		tfAddress.setRequired(true);
		layout.add(tfAddress);
		builder.addComponent("ADDRESS", tfAddress);
		
		return layout;
	}
}
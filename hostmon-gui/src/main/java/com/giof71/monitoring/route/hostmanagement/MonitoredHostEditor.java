package com.giof71.monitoring.route.hostmanagement;

import org.springframework.beans.factory.annotation.Autowired;

import com.giof71.monitoring.editing.EditorDefinition;
import com.giof71.monitoring.editing.impl.SimpleEditor;
import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.HostManagement;
import com.giof71.monitoring.service.HostService;
import com.vaadin.flow.component.textfield.TextField;

@EditorDefinition(modelClass = MonitoredHost.class, requiredQualifier = HostManagement.class)
public abstract class MonitoredHostEditor extends SimpleEditor {
	
	private static final long serialVersionUID = 36897351304630125L;
	
	@Autowired
	private HostService hostService;
	
	protected HostService getHostService() {
		return hostService;
	}
	
	protected final TextField getFriendlyName() {
		return getEditingLayout().getFieldComponent(HostManagementComponentName.FRIENDLY_NAME.name(), TextField.class);
	}
	
	protected final TextField getAddress() {
		return getEditingLayout().getFieldComponent(HostManagementComponentName.ADDRESS.name(), TextField.class);
	}
}
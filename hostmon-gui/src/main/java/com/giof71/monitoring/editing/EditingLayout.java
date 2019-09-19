package com.giof71.monitoring.editing;

import com.vaadin.flow.component.Component;

public interface EditingLayout {
	Component getCreatedComponent();
	<T extends Component> T getFieldComponent(String fieldName, Class<T> componentType);
}

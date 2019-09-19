package com.giof71.monitoring.editing;

import com.vaadin.flow.component.Component;

public interface EditingLayout {
	Component getLayoutComponent();
	<T extends Component> T getComponent(String fieldName, Class<T> componentType);
}

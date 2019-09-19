package com.giof71.monitoring.editing.impl;

import java.util.HashMap;
import java.util.Map;

import com.giof71.monitoring.editing.EditingLayoutCreator;
import com.vaadin.flow.component.Component;

public abstract class SimpleEditingLayoutCreator implements EditingLayoutCreator {
	
	private final Map<String, Component> componentMap = new HashMap<>();
	
	protected void addComponent(String name, Component component) {
		componentMap.put(name, component);
	}
	
	protected <C extends Component> C get(String name, Class<C> type) {
		return type.cast(componentMap.get(name));
	}
}

package com.giof71.monitoring.editing.impl;

import java.util.HashMap;
import java.util.Map;

import com.giof71.monitoring.editing.EditingLayout;
import com.vaadin.flow.component.Component;

public class SimpleEditingLayout implements EditingLayout {
	
	private final Component createdComponent;
	private final Map<String, Component> componentMap = new HashMap<>();
	
	private SimpleEditingLayout(Builder builder) {
		this.createdComponent = builder.createdComponent;
		for (Map.Entry<String, Component> current : builder.componentMap.entrySet()) {
			this.componentMap.put(current.getKey(), current.getValue());
		}
	}

	@Override
	public Component getCreatedComponent() {
		return createdComponent;
	}

	@Override
	public <T extends Component> T getFieldComponent(String fieldName, Class<T> componentType) {
		return componentType.cast(componentMap.get(fieldName));
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		
		private Component createdComponent;
		private final Map<String, Component> componentMap = new HashMap<>();
		
		public Builder addComponent(String name, Component component) {
			componentMap.put(name, component);
			return this;
		}
		
		public Builder createdComponent(Component createdComponent) {
			this.createdComponent = createdComponent;
			return this;
		}
		
		public SimpleEditingLayout build() {
			return new SimpleEditingLayout(this);
		}
	}
}

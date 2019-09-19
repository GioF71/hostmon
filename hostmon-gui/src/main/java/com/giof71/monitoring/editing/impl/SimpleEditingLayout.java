package com.giof71.monitoring.editing.impl;

import java.util.HashMap;
import java.util.Map;

import com.giof71.monitoring.editing.EditingLayout;
import com.vaadin.flow.component.Component;

public class SimpleEditingLayout implements EditingLayout {
	
	private final Component layoutComponent;
	private final Map<String, Component> componentMap = new HashMap<>();
	
	private SimpleEditingLayout(Builder builder) {
		this.layoutComponent = builder.layoutComponent;
		for (Map.Entry<String, Component> current : builder.componentMap.entrySet()) {
			this.componentMap.put(current.getKey(), current.getValue());
		}
	}

	@Override
	public Component getLayoutComponent() {
		return layoutComponent;
	}

	@Override
	public <T extends Component> T getComponent(String fieldName, Class<T> componentType) {
		return componentType.cast(componentMap.get(fieldName));
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		
		private Component layoutComponent;
		private final Map<String, Component> componentMap = new HashMap<>();
		
		public Builder addComponent(String name, Component component) {
			componentMap.put(name, component);
			return this;
		}
		
		public Builder layoutComponent(Component value) {
			this.layoutComponent = value;
			return this;
		}
		
		public SimpleEditingLayout build() {
			return new SimpleEditingLayout(this);
		}
	}
}

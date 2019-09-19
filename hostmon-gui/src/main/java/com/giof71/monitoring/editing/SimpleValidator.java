package com.giof71.monitoring.editing;

import com.vaadin.flow.component.Component;

public interface SimpleValidator {
	void validate(Component component) throws ValidationException;
}

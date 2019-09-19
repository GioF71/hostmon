package com.giof71.monitoring.editing.simplevalidator;

import com.giof71.monitoring.editing.SimpleValidator;
import com.giof71.monitoring.editing.ValidationException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;

@org.springframework.stereotype.Component
public class TextMustNotBeEmpty implements SimpleValidator {

	@Override
	public void validate(Component component) throws ValidationException {
		if (component != null) {
			Class<? extends Component> cz = component.getClass();
			if (TextField.class.isAssignableFrom(cz)) {
				String text = TextField.class.cast(component).getValue();
				if (text == null || text.length() == 0) {
					throw new EmptyTextException();
				}
			}
		}
	}

}

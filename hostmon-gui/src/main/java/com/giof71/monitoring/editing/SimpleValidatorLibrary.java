package com.giof71.monitoring.editing;

public interface SimpleValidatorLibrary {
	SimpleValidator get(Class<? extends SimpleValidator> validatorType);
}

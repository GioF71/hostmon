package com.giof71.monitoring.conversion;

public interface Converter {
	Class<?> from();
	Class<?> to();
	Object convert(Object from);
}

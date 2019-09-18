package com.giof71.monitoring.conversion;

public interface ConverterLibrary {
	<From, To> TypedConverter<From, To> getConverter(Class<From> fromType, Class<To> toType);
	<From, To> To convert(From from, Class<To> toType);
	<From, To> To convert(From from, Class<From> fromType, Class<To> toType);
}

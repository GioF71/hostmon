package com.giof71.monitoring.conversion;

public interface TypedConverter<From, To> {
	Class<From> from();
	Class<To> to();
	To convert(From from);
}

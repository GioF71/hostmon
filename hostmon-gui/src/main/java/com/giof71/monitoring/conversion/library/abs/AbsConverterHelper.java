package com.giof71.monitoring.conversion.library.abs;

import com.giof71.monitoring.conversion.Converter;

public abstract class AbsConverterHelper<From, To> implements Converter {
	
	protected abstract Class<From> getTypedFrom();
	protected abstract Class<To> getTypedTo();
	
	protected abstract To doConvert(From f);

	@Override
	public Class<?> from() {
		return getTypedFrom();
	}

	@Override
	public Class<?> to() {
		return getTypedTo();
	}

	@Override
	public Object convert(Object f) {
		return getTypedTo().cast(doConvert(getTypedFrom().cast(f)));
	}

}

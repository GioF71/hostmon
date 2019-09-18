package com.giof71.monitoring.conversion.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.conversion.Converter;
import com.giof71.monitoring.conversion.ConverterLibrary;
import com.giof71.monitoring.conversion.TypedConverter;

@Component
public class ConverterLibraryImpl implements ConverterLibrary {
	
	@Autowired
	private List<Converter> converterList;
	
	private final BySourceType bySourceType = new BySourceType();
	
	@PostConstruct
	private void postConstruct() {
		for (Converter current : Optional.ofNullable(converterList).orElse(Collections.emptyList())) {
			ByTargetType byTargetType = bySourceType.get(current.from());
			if (byTargetType == null) {
				byTargetType = new ByTargetType();
				bySourceType.put(current.from(), byTargetType);
			}
			if (byTargetType.containsKey(current.to())) {
				// duplicate converter, throw
				throw new RuntimeException(String.format("Another %s [%s -> %s] already exists", 
					Converter.class.getSimpleName(),
					current.from().getSimpleName(),
					current.to().getSimpleName()));
			}
			byTargetType.put(current.to(), current);
		}
	}
	
	private Converter findConverter(Class<?> from, Class<?> to) {
		return Optional.ofNullable(bySourceType.get(from))
			.map(t -> t.get(to))
			.orElseThrow(() -> new RuntimeException(String.format("Cannot find %s [%s -> %s]", 
				Converter.class.getSimpleName(),
				from.getSimpleName(),
				to.getSimpleName())));
	}

	@Override
	public <From, To> To convert(From from, Class<To> toType) {
		return toType.cast(findConverter(from.getClass(), toType).convert(from));
	}

	@Override
	public <From, To> To convert(From from, Class<From> fromType, Class<To> toType) {
		return toType.cast(findConverter(fromType, toType).convert(from));
	}

	@Override
	public <F, T> TypedConverter<F, T> getConverter(Class<F> fromType, Class<T> toType) {
		Converter converter = findConverter(fromType, toType);
		return new TypedConverter<F, T>() {

			@Override
			public Class<F> from() {
				return fromType;
			}

			@Override
			public Class<T> to() {
				return toType;
			}

			@Override
			public T convert(F from) {
				return toType.cast(converter.convert(fromType.cast(from)));
			}
		};
	}
}

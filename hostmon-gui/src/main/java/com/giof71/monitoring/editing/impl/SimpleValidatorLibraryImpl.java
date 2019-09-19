package com.giof71.monitoring.editing.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.editing.SimpleValidator;
import com.giof71.monitoring.editing.SimpleValidatorLibrary;

@Component
public class SimpleValidatorLibraryImpl implements SimpleValidatorLibrary {
	
	@Autowired
	private List<SimpleValidator> list;
	
	private final Map<Class<? extends SimpleValidator>, SimpleValidator> byType = new HashMap<>();
	
	@PostConstruct
	private void postConstruct() {
		for (SimpleValidator current : Optional.ofNullable(list).orElse(Collections.emptyList())) {
			byType.put(current.getClass(), current);
		}
	}

	@Override
	public SimpleValidator get(Class<? extends SimpleValidator> validatorType) {
		return byType.get(validatorType);
	}
}

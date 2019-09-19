package com.giof71.monitoring.editing.impl;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.editing.EditingLayoutCreator;
import com.giof71.monitoring.editing.EditingLayoutCreatorLibrary;
import com.giof71.monitoring.editing.LayoutCreatorDefinition;
import com.giof71.monitoring.route.Action;
import com.giof71.monitoring.util.AnnotationScanner;

@Component
public class EditingLayoutCreatorLibraryImpl implements EditingLayoutCreatorLibrary {
	
	@Autowired
	private AnnotationScanner annotationScanner;
	
	@Autowired(required = false)
	private List<EditingLayoutCreator> editingLayoutCreatorList;
	
	private final ByQualifier byQualifier = new ByQualifier();
	
	@PostConstruct
	private void postConstruct() {
		for (EditingLayoutCreator current : Optional.ofNullable(editingLayoutCreatorList).orElse(Collections.emptyList())) {
			LayoutCreatorDefinition lcd = annotationScanner.scanForTypeAnnotation(current.getClass(), EditingLayoutCreator.class, LayoutCreatorDefinition.class).get();
			if (lcd != null) {
				ByAction byAction = byQualifier.get(lcd.requiredQualifier());
				if (byAction == null) {
					byAction = new ByAction();
					byQualifier.put(lcd.requiredQualifier(), byAction);
				}
				byAction.put(lcd.action(), current);
			}
		}
	}

	@Override
	public EditingLayoutCreator get(Class<? extends Annotation> editorQualifier, Action editorAction) {
		return Optional.ofNullable(byQualifier.get(editorQualifier))
			.map(byAction -> byAction.get(editorAction))
			.orElse(null);
	}

}

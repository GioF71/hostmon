package com.giof71.monitoring.editing;

import java.lang.annotation.Annotation;

import com.giof71.monitoring.route.Action;

public interface EditingLayoutCreatorLibrary {
	EditingLayoutCreator get(Class<? extends Annotation> editorQualifier, Action editorAction);
}

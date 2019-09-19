package com.giof71.monitoring.editing;

import java.lang.annotation.Annotation;

public interface EditingLayoutCreatorLibrary {
	EditingLayoutCreator get(Class<? extends Annotation> editorQualifier, Action editorAction);
}

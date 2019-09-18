package com.giof71.monitoring.editing;

import java.lang.annotation.Annotation;

public @interface EditorDefinition {
	Class<?> modelClass();
	Class<? extends Annotation> requiredQualifier();
}

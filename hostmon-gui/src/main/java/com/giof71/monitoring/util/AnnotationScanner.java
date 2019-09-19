package com.giof71.monitoring.util;

import java.lang.annotation.Annotation;
import java.util.Optional;

public interface AnnotationScanner {
	<T extends Annotation> Optional<T> scanForTypeAnnotation(Class<?> targetClass, Class<?> limitClass, Class<T> annotationType);
}

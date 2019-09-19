package com.giof71.monitoring.util.impl;

import java.lang.annotation.Annotation;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.giof71.monitoring.util.AnnotationScanner;

@Component
public class AnnotationScannerImpl implements AnnotationScanner {
	
	@Override
	public <T extends Annotation> Optional<T> scanForTypeAnnotation(Class<?> targetClass, Class<?> limitClass, Class<T> annotationType) {
		Class<?> currentTarget = targetClass;
		T result = null;
		while (result == null && !(currentTarget == null || currentTarget.equals(limitClass) || currentTarget.equals(Object.class))) {
			result = currentTarget.getAnnotation(annotationType);
			if (result == null) {
				currentTarget = currentTarget.getSuperclass();	
			}
		}
		return Optional.ofNullable(result);
	}

}

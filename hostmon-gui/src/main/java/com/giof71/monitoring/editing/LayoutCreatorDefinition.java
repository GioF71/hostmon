package com.giof71.monitoring.editing;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

import com.giof71.monitoring.route.Action;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface LayoutCreatorDefinition {
	Class<? extends Annotation> requiredQualifier();
	Action action();
}

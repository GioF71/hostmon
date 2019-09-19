package com.giof71.monitoring.route;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface ActionDelete {

}

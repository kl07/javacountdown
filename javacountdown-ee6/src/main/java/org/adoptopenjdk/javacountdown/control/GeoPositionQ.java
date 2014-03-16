package org.adoptopenjdk.javacountdown.control;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface GeoPositionQ {}


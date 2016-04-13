package com.yoda.yodao.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Transactional
 */
@Target({METHOD}) 
@Retention(CLASS)

public @interface Transactional {}

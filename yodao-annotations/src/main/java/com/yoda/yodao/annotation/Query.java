package com.yoda.yodao.annotation;


/**
 * SQL Query
 */
public @interface Query {

	String value() default "";

}
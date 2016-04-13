package com.yoda.yodao.internal.query;

public class YoGroupBy {

	private String field;
	
	public YoGroupBy() {
		this(null);
	}

	public YoGroupBy(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "YoGroupBy [field=" + field + "]";
	}

}

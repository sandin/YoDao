package com.yoda.yodao.internal.query;

public class YoHaving {

	private String field;

	public YoHaving() {
		this(null);
	}

	public YoHaving(String field) {
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
		return "YoHaving [field=" + field + "]";
	}

}

package com.yoda.yodao.internal.query;

public class YoSelection {
	
	private String fieldName;
	
	private String arg;
	
	private YoSelection and;

	private YoSelection or;
	
	public YoSelection(String fieldName, String arg) {
		this.fieldName = fieldName;
		this.arg = arg;
	}
	
	public YoSelection and(YoSelection selection) {
		and = selection;
		return this;
	}

	public YoSelection or(YoSelection selection) {
		or = selection;
		return this;
	}
	

}

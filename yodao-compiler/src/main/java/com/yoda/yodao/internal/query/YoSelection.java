package com.yoda.yodao.internal.query;

public class YoSelection {

	private String field;

	private String arg;

	private YoSelection and;

	private YoSelection or;

	private boolean isOr = false;

	public YoSelection() {
		this(null, null);
	}

	public YoSelection(String field, String arg) {
		this.field = field;
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

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

	public YoSelection getAnd() {
		return and;
	}

	public void setAnd(YoSelection and) {
		this.and = and;
	}

	public YoSelection getOr() {
		return or;
	}

	public void setOr(YoSelection or) {
		this.or = or;
	}

	public boolean isOr() {
		return isOr;
	}

	public void setIsOr(boolean isOr) {
		this.isOr = isOr;
	}

	@Override
	public String toString() {
		return "YoSelection [field=" + field + ", arg=" + arg + ", isOr="
				+ isOr + "]";
	}

}

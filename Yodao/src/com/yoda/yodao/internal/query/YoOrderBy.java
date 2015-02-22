package com.yoda.yodao.internal.query;

public class YoOrderBy {

	public enum Order {
		DESC, ASC
	}

	private String field;

	private Order order;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "YoOrderBy [field=" + field + ", order=" + order + "]";
	}

}

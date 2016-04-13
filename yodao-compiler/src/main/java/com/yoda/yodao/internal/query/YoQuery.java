package com.yoda.yodao.internal.query;

import java.util.ArrayList;
import java.util.List;

public class YoQuery {

	public enum CRUD {
		CREATE, READ, UPDATE, DELETE, COUNT, RAW_SQL
	}

	private CRUD crud;

	private String name;

	private String entity;

	private String sql;

	private List<YoSelection> selections = new ArrayList<YoSelection>();
	private List<YoGroupBy> groupBys = new ArrayList<YoGroupBy>();
	private List<YoHaving> havings = new ArrayList<YoHaving>();
	private List<YoOrderBy> orderBys = new ArrayList<YoOrderBy>();

	public YoQuery selection(YoSelection section) {
		selections.add(section);
		return this;
	}

	public YoQuery having(YoHaving having) {
		havings.add(having);
		return this;
	}

	public YoQuery groupBy(YoGroupBy groupBy) {
		groupBys.add(groupBy);
		return this;
	}

	public YoQuery orderBys(YoOrderBy orderBy) {
		orderBys.add(orderBy);
		return this;
	}

	public CRUD getCrud() {
		return crud;
	}

	public void setCrud(CRUD crud) {
		this.crud = crud;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public List<YoSelection> getSelections() {
		return selections;
	}

	public void setSelections(List<YoSelection> selections) {
		this.selections = selections;
	}

	public List<YoGroupBy> getGroupBys() {
		return groupBys;
	}

	public void setGroupBys(List<YoGroupBy> groupBys) {
		this.groupBys = groupBys;
	}

	public List<YoHaving> getHavings() {
		return havings;
	}

	public void setHavings(List<YoHaving> havings) {
		this.havings = havings;
	}

	public List<YoOrderBy> getOrderBys() {
		return orderBys;
	}

	public void setOrderBys(List<YoOrderBy> orderBys) {
		this.orderBys = orderBys;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "YoQuery [crud=" + crud + ", name=" + name + ", entity="
				+ entity + ", sql=" + sql + ", selections=" + selections
				+ ", groupBys=" + groupBys + ", havings=" + havings
				+ ", orderBys=" + orderBys + "]";
	}

}

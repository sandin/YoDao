package com.yoda.yodao.internal.query;

import java.util.ArrayList;
import java.util.List;


public class YoQuery {
	
	public enum CRUD {
		CREATE,
		READ,
		UPDATE,
		DELETE,
	}
	
	private CRUD crud;
	
	private String name;
	
	private String entity;
	
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
	
}

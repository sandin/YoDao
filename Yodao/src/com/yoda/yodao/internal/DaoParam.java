package com.yoda.yodao.internal;

public class DaoParam {

	private String type;

	private String name;
	
	public DaoParam() {
		
	}
	
	public DaoParam(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DaoParam [type=" + type + ", name=" + name + "]";
	}

}

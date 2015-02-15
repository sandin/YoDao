package com.yoda.yodao.internal;

import java.util.List;

public class DaoInfo {

	private Clazz entityClass;

	private Clazz daoClass;

	private List<DaoMethod> methods;

	public Clazz getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Clazz entityClass) {
		this.entityClass = entityClass;
	}

	public List<DaoMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<DaoMethod> methods) {
		this.methods = methods;
	}

	public Clazz getDaoClass() {
		return daoClass;
	}

	public void setDaoClass(Clazz daoClass) {
		this.daoClass = daoClass;
	}

	@Override
	public String toString() {
		return "DaoInfo [entityClass=" + entityClass + ", daoClass=" + daoClass
				+ ", methods=" + methods + "]";
	}

}

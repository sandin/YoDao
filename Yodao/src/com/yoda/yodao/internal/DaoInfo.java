package com.yoda.yodao.internal;

import java.util.List;

import com.yoda.yodao.internal.query.YoQuery;

public class DaoInfo {

	private Clazz entityClass;

	private Clazz daoClass;

	private List<DaoMethod> methods;

	private boolean isInterface;

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

	public boolean isInterface() {
		return isInterface;
	}

	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	@Override
	public String toString() {
		return "DaoInfo [entityClass=" + entityClass + ", daoClass=" + daoClass
				+ ", methods=" + methods + ", isInterface=" + isInterface + "]";
	}

}

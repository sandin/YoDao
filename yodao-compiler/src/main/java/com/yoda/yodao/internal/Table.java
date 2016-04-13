package com.yoda.yodao.internal;

import java.util.List;

import javax.lang.model.element.Element;

/**
 * maped table
 * 
 * @author lds
 *
 */
public class Table {

	private String tableName;

	private Clazz daoClass;

	private Clazz entityClass;

	private DaoInfo daoInfo;

	private List<Field> fields;

	private Element element;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String brewJava() {
		return new DaoGenerator().generate(this);
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Clazz getDaoClass() {
		return daoClass;
	}

	public void setDaoClass(Clazz daoClass) {
		this.daoClass = daoClass;
	}

	public Clazz getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Clazz entityClass) {
		this.entityClass = entityClass;
	}

	public Field getPKField() {
		if (fields != null) {
			for (Field field : fields) {
				if (field.isId()) {
					return field;
				}
			}
		}
		return null;
	}

	public DaoInfo getDaoInfo() {
		return daoInfo;
	}

	public void setDaoInfo(DaoInfo daoInfo) {
		this.daoInfo = daoInfo;
	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", daoClass=" + daoClass
				+ ", entityClass=" + entityClass + ", daoInfo=" + daoInfo
				+ ", fields=" + fields + "]";
	}

}

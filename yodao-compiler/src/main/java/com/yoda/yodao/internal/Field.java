package com.yoda.yodao.internal;

/**
 * field of table
 * 
 * @author lds
 *
 */
public class Field {

	public enum IdGenerator {
		AUTO, UUID
	}

	/**
	 * Java field Name
	 */
	private String fieldName;

	/**
	 * Java field Type
	 */
	private String fieldType;

	/**
	 * Database column name
	 */
	private String columnName;

	/**
	 * Database column type
	 */
	private String columnType;

	/**
	 * IS NOT NULL
	 */
	private boolean nullable = true;

	/**
	 * IS PK
	 */
	private boolean isId = false;

	private IdGenerator idGenerator = IdGenerator.AUTO;

	public Field() {

	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public boolean isId() {
		return isId;
	}

	public void setIsId(boolean isId) {
		this.isId = isId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * method name of this field's setter
	 */
	public String getSetterMethodName() {
		return "set" + Utils.upperCaseFirstLetter(fieldName);
	}

	/**
	 * method name of this field's getter
	 */
	public String getGetterMethodName() {
		return "get" + Utils.upperCaseFirstLetter(fieldName);
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public IdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Override
	public String toString() {
		return "Field [fieldName=" + fieldName + ", fieldType=" + fieldType
				+ ", columnName=" + columnName + ", columnType=" + columnType
				+ ", nullable=" + nullable + ", isId=" + isId
				+ ", idGenerator=" + idGenerator + "]";
	}

}

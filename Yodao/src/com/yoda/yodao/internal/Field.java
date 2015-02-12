package com.yoda.yodao.internal;

/**
 * field of table
 * 
 * @author lds
 *
 */
public class Field {

	private String columnName;

	private String columnType;

	private boolean isId = false;

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

	@Override
	public String toString() {
		return "Field [columnName=" + columnName + ", columnType=" + columnType
				+ ", isId=" + isId + "]";
	}

}

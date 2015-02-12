package com.yoda.yodao.internal;

import org.junit.Assert;

public class DaoGenerator  {

	public static String generate(Table table) {
		if (table == null) {
			throw new IllegalArgumentException("arg0[table] cann't be null");
		}

		StringBuilder sb = new StringBuilder();
		sb.append("package " + table.getDaoClass().packageName + ";\n");
		sb.append("import com.yoda.yodao.BaseDao;\n");
		sb.append("import " + table.getEntityClass().getCanonicalName() + ";\n");
		sb.append("\n");
		sb.append("public class " + table.getDaoClass().className + " extends BaseDao<" + table.getEntityClass().className + "> {\n;");
		sb.append("}\n");

		return sb.toString();
	}

	public static void main(String[] args) {
		Clazz clazz = YodaoProcessor.parseToDaoClazz("com.lds.model.User");
		Assert.assertEquals("com.lds.dao", clazz.packageName);
		Assert.assertEquals("User", clazz.className);
		System.out.println("class: " + clazz.className);
		System.out.println("package: " + clazz.packageName);
		
		clazz = YodaoProcessor.parseToEntityClazz("com.lds.model.User");
		Assert.assertEquals("com.lds.model", clazz.packageName);
		Assert.assertEquals("User", clazz.className);
		System.out.println("class: " + clazz.className);
		System.out.println("package: " + clazz.packageName);
	}

	
}

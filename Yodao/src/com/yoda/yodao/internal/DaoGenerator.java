package com.yoda.yodao.internal;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

public class DaoGenerator {
	private static final String TAB = "    ";
	private static final String TAB2 = TAB + TAB;
	private static final String TAB3 = TAB + TAB + TAB;

	public static String generate(Table table) {
		if (table == null) {
			throw new IllegalArgumentException("arg0[table] cann't be null");
		}

		StringBuilder sb = new StringBuilder();
		sb.append("package " + table.getDaoClass().packageName + ";\n");
		sb.append("\n");
		sb.append("import com.yoda.yodao.BaseDao;\n");
		sb.append("\n");
		sb.append("import android.database.Cursor;\n");
		sb.append("import android.content.ContentValues;\n");
		sb.append("import android.database.sqlite.SQLiteOpenHelper;\n");
		sb.append("\n");
		sb.append("import " + table.getEntityClass().getCanonicalName() + ";\n");
		sb.append("\n");
		sb.append("public class " + table.getDaoClass().className
				+ " extends BaseDao<" + table.getEntityClass().className
				+ "> {\n");
		sb.append("\n");
		genConstructor(sb, table);
		genCursorToObject(sb, table);
		genObjectToValues(sb, table);
		genGetPK(sb, table);
		genSetPK(sb, table);
		genGetTableName(sb, table);
		sb.append("\n");
		sb.append("}\n");

		return sb.toString();
	}

	private static void genConstructor(StringBuilder sb, Table table) {
		Clazz clazz = table.getDaoClass();
		sb.append(TAB
				+ String.format("public %s(SQLiteOpenHelper openHelper) {\n",
						clazz.className));
		sb.append(TAB2 + "super(openHelper);\n");
		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	private static void genCursorToObject(StringBuilder sb, Table table) {
		Clazz clazz = table.getEntityClass();
		sb.append(TAB + "@Override\n");
		sb.append(TAB
				+ String.format(
						"public %s cursorToObject(Cursor cursor, String[] columns) {\n",
						clazz.className));
		sb.append(TAB2 + "User user = new User();\n");
		sb.append(TAB2 + "return user;\n");
		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	private static void genObjectToValues(StringBuilder sb, Table table) {
		Clazz clazz = table.getEntityClass();
		sb.append(TAB + "@Override\n");
		sb.append(TAB
				+ String.format(
						"public ContentValues objectToValues(%s obj) {\n",
						clazz.className));
		sb.append(TAB2 + "ContentValues v = new ContentValues();\n");
		sb.append(TAB2 + "return v;\n");
		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	private static void genGetPK(StringBuilder sb, Table table) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("T", table.getEntityClass().className);
		sb.append(format(FORMAT_GET_PK, args));
	}

	private static void genSetPK(StringBuilder sb, Table table) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("T", table.getEntityClass().className);
		sb.append(format(FORMAT_SET_PK, args));
	}
	
	private static void genGetTableName(StringBuilder sb, Table table) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("tableName", table.getTableName());
		sb.append(format(FORMAT_GET_TABLE_NAME, args));
	}

	public static final String FORMAT_GET_PK = //
	TAB + "@Override\n" //
			+ TAB + "public long getPK(::T:: entity) {\n" //
			+ TAB2 + "return entity.getId();\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static final String FORMAT_SET_PK = //
	TAB + "@Override\n" //
			+ TAB + "public ::T:: setPK(::T:: entity, long id) {\n" //
			+ TAB2 + "entity.setId(id);\n" //
			+ TAB2 + "return entity;\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static final String FORMAT_GET_TABLE_NAME = //
	TAB + "@Override\n" //
			+ TAB + "public String getTableName() {\n" //
			+ TAB2 + "return \"::tableName::\";\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	private static String format(String format, Map<String, Object> args) {
		return TemplateEngine.render(format, args);
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

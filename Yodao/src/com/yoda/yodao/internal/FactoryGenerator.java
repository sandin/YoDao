package com.yoda.yodao.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FactoryGenerator {

	public static final String DAO_FACTORY_CLASS_NAME = "DaoFactory";
	public static final String DAO_FACTORY_PACKAGE_NAME = "com.yoda.yodao";

	private static final String TAB = "    ";
	private static final String TAB2 = TAB + TAB;
	private static final String TAB3 = TAB + TAB + TAB;

	public static String getCanonicalName() {
		return DAO_FACTORY_PACKAGE_NAME + "." + DAO_FACTORY_CLASS_NAME;
	}

	public static String generate(List<Table> tables) {
		if (tables == null || tables.size() == 0) {
			return null;
		}
		// remove table which has no DAO maped
		ArrayList<Table> localTables = new ArrayList<Table>();
		for (Table table : tables) {
			if (table.getDaoInfo() != null) {
				localTables.add(table);
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("package " + DAO_FACTORY_PACKAGE_NAME + ";\n");
		sb.append("\n");
		sb.append("import com.yoda.yodao.BaseDao;\n");
		for (Table table : localTables) {
			DaoInfo daoInfo = table.getDaoInfo();
			sb.append("import " + daoInfo.getDaoClass().getCanonicalName()
					+ ";\n");
			sb.append("import " + table.getDaoClass().getCanonicalName()
					+ ";\n");
		}
		sb.append("\n");
		sb.append("import android.database.Cursor;\n");
		sb.append("import android.content.ContentValues;\n");
		sb.append("import android.database.sqlite.SQLiteDatabase;\n");
		sb.append("import android.database.sqlite.SQLiteOpenHelper;\n");
		sb.append("\n");
		sb.append("/**\n");
		sb.append(" * AUTO GENERATE BY YODAO, DO NOT MODIFY IT! \n");
		sb.append(" * " + new Date().toString() + "\n");
		sb.append(" */\n");
		sb.append("public final class " + DAO_FACTORY_CLASS_NAME + " {\n");
		sb.append("\n");
		genConstructor(sb);
		genOnCreateTable(sb, localTables);
		genOnUpgradeTable(sb, localTables);
		genCreate(sb, localTables);
		sb.append("\n");
		sb.append("}\n");

		return sb.toString();
	}

	private static void genConstructor(StringBuilder sb) {
		// empty private constructor
		sb.append(TAB
				+ String.format("private %s() {\n", DAO_FACTORY_CLASS_NAME));
		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	private static void genOnCreateTable(StringBuilder sb, List<Table> tables) {
		sb.append(TAB
				+ String.format(
						"public static void onCreateTable(SQLiteDatabase db) {\n",
						DAO_FACTORY_CLASS_NAME));
		for (Table table : tables) {
			DaoInfo dao = table.getDaoInfo();
			sb.append(TAB2 + "create(" + dao.getDaoClass().className
					+ ".class).onCreateTable(db);\n");
		}
		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	private static void genOnUpgradeTable(StringBuilder sb, List<Table> tables) {
		sb.append(TAB
				+ String.format(
						"public static void onUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {\n",
						DAO_FACTORY_CLASS_NAME));
		for (Table table : tables) {
			DaoInfo dao = table.getDaoInfo();
			sb.append(TAB2 + "create(" + dao.getDaoClass().className
					+ ".class).onUpgradeTable(db, oldVersion, newVersion);\n");
		}
		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	private static void genCreate(StringBuilder sb, List<Table> tables) {
		// for empty constructor
		sb.append(TAB + "@SuppressWarnings(\"unchecked\")\n");
		sb.append(TAB + "public static <T> T create(Class<T> daoClass) {\n");
		for (Table table : tables) {
			DaoInfo dao = table.getDaoInfo();
			sb.append(TAB2 + "if (daoClass == " + dao.getDaoClass().className
					+ ".class) {\n");
			sb.append(TAB3 + "return (T) new " + table.getDaoClass().className
					+ "();\n");
			sb.append(TAB2 + "}\n");
		}
		sb.append(TAB2
				+ "throw new IllegalArgumentException(daoClass.getSimpleName() + \" cann't found.\");\n");
		sb.append(TAB + "}\n");
		sb.append("\n");

		sb.append(TAB + "@SuppressWarnings(\"unchecked\")\n");
		sb.append(TAB
				+ "public static <T> T create(Class<T> daoClass, SQLiteOpenHelper openHelper) {\n");
		for (Table table : tables) {
			DaoInfo dao = table.getDaoInfo();
			sb.append(TAB2 + "if (daoClass == " + dao.getDaoClass().className
					+ ".class) {\n");
			sb.append(TAB3 + "return (T) new " + table.getDaoClass().className
					+ "(openHelper);\n");
			sb.append(TAB2 + "}\n");
		}
		sb.append(TAB2
				+ "throw new IllegalArgumentException(daoClass.getSimpleName() + \" cann't found.\");\n");
		sb.append(TAB + "}\n");
		sb.append("\n");

	}

}

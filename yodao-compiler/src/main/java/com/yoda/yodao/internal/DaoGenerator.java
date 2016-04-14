package com.yoda.yodao.internal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yoda.yodao.internal.query.YoGroupBy;
import com.yoda.yodao.internal.query.YoOrderBy;
import com.yoda.yodao.internal.query.YoOrderBy.Order;
import com.yoda.yodao.internal.query.YoQuery;
import com.yoda.yodao.internal.query.YoQuery.CRUD;
import com.yoda.yodao.internal.query.YoSelection;

public class DaoGenerator {
	private static final String TAB = "    ";
	private static final String TAB2 = TAB + TAB;
	private static final String TAB3 = TAB + TAB + TAB;

	public static final String FORMAT_GET_PK = //
	TAB + "@Override\n" //
			+ TAB + "public ::PKType:: getPK(::T:: entity) {\n" //
			+ TAB2 + "return entity.get::PK::();\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static final String FORMAT_SET_PK = //
	TAB + "@Override\n" //
			+ TAB + "public ::T:: setPK(::T:: entity, ::PKType:: id) {\n" //
			+ TAB2 + "if (id != null) {\n" //
			+ TAB3 + "entity.set::PK::(id);\n" //
			+ TAB2 + "}\n" + TAB2 + "return entity;\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static final String FORMAT_GET_PK_CLASS = //
	TAB + "@Override\n" //
			+ TAB + "public Class<?> getPKClass() {\n" //
			+ TAB2 + "return ::PKType::;\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static String FORMAT_GET_TABLE_NAME = //
	TAB + "@Override\n" //
			+ TAB + "public String getTableName() {\n" //
			+ TAB2 + "return TABLE_NAME;\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static String FORMAT_GET_CREATE_TABLE_SQL = //
	TAB + "@Override\n" //
			+ TAB + "public String getCreateTableSql() {\n" //
			+ TAB2 + "return CREATE_TABLE_SQL;\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static String FORMAT_ON_CREATE_TABLE = //
	TAB + "@Override\n" //
			+ TAB + "public void onCreateTable(SQLiteDatabase db) {\n" //
			+ TAB2 + "db.execSQL(this.getCreateTableSql());\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static String FORMAT_ON_UPGRADE_TABLE = //
	TAB
			+ "@Override\n" //
			+ TAB
			+ "public void onUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {\n" //
			+ TAB2
			+ "db.execSQL(\"DROP TABLE IF EXISTS \" + this.getTableName());\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static String FORMAT_CURSOR_TO_OBJECT = //
	TAB
			+ "@Override\n" //
			+ TAB
			+ "public ::T:: cursorToObject(Cursor c, String[] columns) {\n" //
			+ TAB2 + "::T:: obj = new ::T::();\n" //
			+ "::setValues::" //
			+ TAB2 + "return obj;\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public static String FORMAT_OBJECT_TO_VALUES = //
	TAB + "@Override\n" //
			+ TAB + "public ContentValues objectToValues(::T:: obj) {\n" //
			+ TAB2 + "ContentValues v = new ContentValues();\n" //
			+ "::toValues::" //
			+ TAB2 + "return v;\n" //
			+ TAB + "}\n" //
			+ TAB + "\n";

	public String generate(Table table) throws ProcessException {
		if (table == null) {
			throw new ProcessException(null, "arg0[table] cann't be null");
		}

		DaoInfo dao = table.getDaoInfo();
		String pkClassName = dao.getPkClass();

		StringBuilder sb = new StringBuilder();
		sb.append("package " + table.getDaoClass().packageName + ";\n");
		sb.append("\n");
		sb.append("import com.yoda.yodao.BaseDao;\n");
		sb.append("import " + dao.getDaoClass().getCanonicalName() + ";\n");
		sb.append("\n");
		sb.append("import android.database.Cursor;\n");
		sb.append("import android.content.ContentValues;\n");
		sb.append("import android.database.sqlite.SQLiteDatabase;\n");
		sb.append("import android.database.sqlite.SQLiteOpenHelper;\n");
		sb.append("\n");
		sb.append("import " + table.getEntityClass().getCanonicalName() + ";\n");
		sb.append("\n");
		sb.append("/**\n");
		sb.append(" * AUTO GENERATE BY YODAO, DO NOT MODIFY IT! \n");
		sb.append(" * " + new Date().toString() + "\n");
		sb.append(" */\n");
		if (dao.isInterface()) {
			sb.append("public class " + table.getDaoClass().className
					+ " extends BaseDao<" + table.getEntityClass().className
					+ ", " + pkClassName + "> implements "
					+ dao.getDaoClass().className + " {\n");
		} else {
			sb.append("public class " + table.getDaoClass().className
					+ " extends " + dao.getDaoClass().className + " {\n");
		}
		sb.append("\n");
		genStaticFields(sb, table);
		genConstructor(sb, table);
		genCursorToObject(sb, table);
		genObjectToValues(sb, table);
		genGetPK(sb, table);
		genSetPK(sb, table);
		genGetPKClass(sb, table);
		genGetTableName(sb, table);
		genGetCreateTableSql(sb, table);
		genOnCreateTable(sb, table);
		genOnUpgradeTable(sb, table);
		genMethods(sb, table);
		sb.append("\n");
		sb.append("}\n");

		return sb.toString();
	}

	private void genMethods(StringBuilder sb, Table table) {
		DaoInfo dao = table.getDaoInfo();
		List<DaoMethod> methods = dao.getMethods();
		if (methods != null) {
			for (DaoMethod method : methods) {
				genMethod(sb, method);
			}
		}
	}

	private void genMethod(StringBuilder sb, DaoMethod method) {
		YoQuery query = method.getQuery();
		CRUD crud = query.getCrud();
		if (query == null || crud == null) {
			return;
		}

		DaoParam[] params = method.getMethodParams();
		sb.append(TAB + "@Override\n");
		sb.append(TAB + "public " + method.getReturnType() + " "
				+ method.getMethodName() + "(");
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				DaoParam p = params[i];
				sb.append(p.getType() + " arg" + i);
				if (i < params.length - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append(") {\n");

		int argsOffset = 0;
		if (crud == CRUD.UPDATE) {
			argsOffset = 1;
		}

		// Selections
		String selection = "null";
		String selectionArgs = "null";
		List<YoSelection> selections = query.getSelections();
		if (selections != null && selections.size() > 0) {
			selection = "";
			selectionArgs = "";
			String args = "";
			for (int i = 0; i < selections.size(); i++) {
				YoSelection where = selections.get(i);
				if (i > 0) {
					selection += where.isOr() ? " OR " : " AND ";
				}
				selection += Utils.toLowerCase(where.getField()) + " = ? ";
				int argIndex = Integer.parseInt(where.getArg()) + argsOffset;
				args += "String.valueOf(arg" + argIndex + "), ";
			}
			selectionArgs = "new String[] { " + args + " }";
			selection = "\"" + selection + "\"";
		}

		// GroupBys
		String groupBy = "null";
		List<YoGroupBy> groupBys = query.getGroupBys();
		if (groupBys != null && groupBys.size() > 0) {
			groupBy = "";
			for (int i = 0; i < groupBys.size(); i++) {
				YoGroupBy group = groupBys.get(i);
				if (i > 0) {
					selection += " AND ";
				}
				groupBy += Utils.toLowerCase(group.getField());
			}
			groupBy = "\"" + groupBy + "\"";
		}

		// Havings
		String having = "null"; // TODO: having

		// Orders
		String orderBy = "null";
		List<YoOrderBy> orderBys = query.getOrderBys();
		if (orderBys != null && orderBys.size() > 0) {
			orderBy = "";
			for (int i = 0; i < orderBys.size(); i++) {
				YoOrderBy order = orderBys.get(i);
				if (i > 0) {
					orderBy += " AND ";
				}
				orderBy += Utils.toLowerCase(order.getField());
				orderBy += order.getOrder() == Order.DESC ? " DESC " : "";
			}
			orderBy = "\"" + orderBy + "\"";
		}

		String returnType = method.getReturnType();
		boolean isList = Utils.isListType(returnType);

		// MethodName
		String methodName = null;
		switch (crud) {
		case READ:
			methodName = isList ? "findListByFields" : "findOneByFields";
			sb.append(TAB2
					+ String.format(
							"return %s(/* where */%s, /* args */ %s, /* group by */ %s, /* having */ %s, /* order by */ %s);\n",
							methodName, selection, selectionArgs, groupBy,
							having, orderBy));
			break;
		case DELETE:
			methodName = "deleteByFields";
			sb.append(TAB2
					+ String.format(
							"return %s(/* where */%s, /* args */ %s);\n",
							methodName, selection, selectionArgs));
			break;
		case COUNT:
			methodName = "countByFields";
			sb.append(TAB2
					+ String.format(
							"return %s(/* where */%s, /* args */ %s);\n",
							methodName, selection, selectionArgs));
			break;
		case UPDATE:
			methodName = "updateByFields";
			sb.append(TAB2
					+ String.format(
							"return %s(%s, /* where */%s, /* args */ %s);\n",
							methodName, "arg0", selection, selectionArgs));
			break;
		case CREATE:
			methodName = "save";
			// TODO: create methods
			break;
		case RAW_SQL:
			String sql = query.getSql();
			methodName = null;
			if (isList) {
				methodName = "findListBySql";
			} else if (Long.class.getCanonicalName().equals(returnType)) {
				methodName = "findLongColBySql";
			} else if (long.class.getCanonicalName().equals(returnType)) {
				methodName = "findLongColBySql";
			} else if (Integer.class.getCanonicalName().equals(returnType)) {
				methodName = "findIntColBySql";
			} else if (int.class.getCanonicalName().equals(returnType)) {
				methodName = "findIntColBySql";
			} else if (String.class.getCanonicalName().equals(returnType)) {
				methodName = "findStringColBySql";
			} else {
				methodName = "findOneBySql";
			}

			selectionArgs = getSelectionArgs(params);
			sb.append(TAB2
					+ String.format("return %s(/* sql */%s, /* args */ %s);\n",
							methodName, quote(sql), selectionArgs));
			break;
		default:
			break;
		}

		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	private static String getSelectionArgs(DaoParam[] params) {
		StringBuilder sb = new StringBuilder();
		sb.append("new String[] {");
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				DaoParam p = params[i];
				sb.append("String.valueOf(arg" + i + "), ");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static String quote(String text) {
		return "\"" + text + "\"";
	}

	// STATIC FIELDS
	private void genStaticFields(StringBuilder sb, Table table) {
		String tableName = table.getTableName();
		// Table Name
		sb.append(String.format(TAB
				+ "public final static String TABLE_NAME = \"%s\";\n",
				tableName));
		sb.append("\n");

		// create table SQL
		List<Field> fields = table.getFields();
		sb.append(String
				.format(TAB
						+ "private final static String CREATE_TABLE_SQL = \" CREATE TABLE `%s` (\"\n",
						tableName));
		if (fields != null) {
			String format = TAB3 + "+ \"`%s`\t%s\t%s\t%s\t%s\"\n";
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				String name = field.getColumnName();
				String javaType = field.getFieldType();
				String dbType = mapJavaTypeToDatabaseType(javaType);
				String notNull = field.isNullable() ? "" : "NOT NULL";
				if (field.isId()) {
					dbType += " PRIMARY KEY";
				}
				String defVal = ""; // TODO: default value
				String comma = i < fields.size() - 1 ? "," : "";
				sb.append(String.format(format, name, dbType, notNull, defVal,
						comma));
			}
		}
		sb.append(TAB3 + "+ \");\";\n");
		sb.append("\n");

		if (fields != null) {
			// Indexs
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				sb.append(String.format(TAB
						+ "private final static int %s = %s;\n",
						getColumnIndex(field), i));
			}
			// Columns
			sb.append("\n");
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				sb.append(String.format(TAB
						+ "public final static String %s = \"%s\";\n",
						getColumnName(field), field.getColumnName()));
			}
			sb.append("\n");
		}

	}

	// CONSTRUCTOR
	private void genConstructor(StringBuilder sb, Table table) {
		Clazz clazz = table.getDaoClass();
		sb.append(TAB
				+ String.format("public %s(SQLiteOpenHelper openHelper) {\n",
						clazz.className));
		sb.append(TAB2 + "super(openHelper);\n");
		Field pk = table.getPKField();
		if (pk != null) {
			sb.append(String.format(TAB2 + "setPrimaryKey(%s);\n",
					getColumnName(pk)));
		}
		sb.append(TAB2 + "mTableName = TABLE_NAME;\n");
		sb.append(TAB + "}\n");
		sb.append("\n");

		// empty constructor
		sb.append(TAB + String.format("public %s() {\n", clazz.className));
		sb.append(TAB2 + "this(null);\n");
		sb.append(TAB + "}\n");
		sb.append("\n");
	}

	// cursorToObject
	private void genCursorToObject(StringBuilder sb, Table table) throws ProcessException  {
		Clazz clazz = table.getEntityClass();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("T", clazz.className);
		args.put("setValues", _genToObject(table));
		sb.append(format(FORMAT_CURSOR_TO_OBJECT, args));
	}

	private String _genToObject(Table table) throws ProcessException {
		StringBuilder sb = new StringBuilder();
		List<Field> fields = table.getFields();
		if (fields != null) {
			for (Field field : fields) {
				String getValue = cursorGet(field);
				if (getValue != null) {
					String setter = field.getSetterMethodName();
					sb.append(TAB2
							+ String.format("obj.%s(%s);\n", setter, getValue));
				} else {
					throw new ProcessException(field.getElement(), String.format(
							"Can't support type %s for %s field",
							field.getFieldType(), field.getFieldName()));
				}
			}
		}
		return sb.toString();
	}

	private String cursorGet(Field field) {
		String columnIndex = getColumnIndex(field);
		String javaType = field.getFieldType();
		String dbType = mapJavaTypeToCursorType(javaType);
		if (dbType != null) {
			if (dbType.equals("Date")) {
				// Handle DateTime type
				return String.format("parseDatetime(c.getString(%s))",
						columnIndex);
			} else if (dbType.equals("Boolean")) {
				// Handle Boolean type
				return String.format("parseBoolean(c.getInt(%s))", columnIndex);
			} else {
				// Handle normal type
				return String.format("c.get%s(%s)", dbType, columnIndex);
			}
		}
		return null;
	}

	public static String mapJavaTypeToCursorType(String type) {
		if (type != null) {
			if (type.equals(int.class.getName()) //
					|| type.equals(Integer.class.getName())) {
				return "Int";
			} else if (type.equals(long.class.getName()) //
					|| type.equals(Long.class.getName())) {
				return "Long";
			} else if (type.equals(short.class.getName()) //
					|| type.equals(Short.class.getName())) {
				return "Short";
			} else if (type.equals(float.class.getName()) //
					|| type.equals(Float.class.getName())) {
				return "Float";
			} else if (type.equals(double.class.getName()) //
					|| type.equals(Double.class.getName())) {
				return "Double";
			} else if (type.equals(String.class.getName())) {
				return "String";
			} else if (type.equals(Date.class.getName())) {
				return "Date";
			} else if (type.equals(boolean.class.getName()) //
					|| type.equals(Boolean.class.getName())) {
				return "Boolean";
			}
		}
		// TODO: getBlob
		return null; // unSupport type
	}

	public static String mapJavaTypeToDatabaseType(String type) {
		if (type != null) {
			if (type.equals(int.class.getName()) //
					|| type.equals(Integer.class.getName())) {
				return "INTEGER";
			} else if (type.equals(long.class.getName()) //
					|| type.equals(Long.class.getName())) {
				return "INTEGER";
			} else if (type.equals(short.class.getName()) //
					|| type.equals(Short.class.getName())) {
				return "INTEGER";
			} else if (type.equals(float.class.getName()) //
					|| type.equals(Float.class.getName())) {
				return "REAL";
			} else if (type.equals(double.class.getName()) //
					|| type.equals(Double.class.getName())) {
				return "REAL";
			} else if (type.equals(String.class.getName())) {
				return "TEXT";
			} else if (type.equals(Date.class.getName())) {
				return "TEXT";
			} else if (type.equals(boolean.class.getName()) //
					|| type.equals(Boolean.class.getName())) {
				return "INTEGER";
			}
		}
		// TODO: getBlob
		return null; // unSupport type
	}

	// objectToValues
	private void genObjectToValues(StringBuilder sb, Table table) {
		Clazz clazz = table.getEntityClass();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("T", clazz.className);
		args.put("toValues", _genToValues(table));
		sb.append(format(FORMAT_OBJECT_TO_VALUES, args));
	}

	private String _genToValues(Table table) {
		StringBuilder sb = new StringBuilder();
		List<Field> fields = table.getFields();
		if (fields != null) {
			for (Field field : fields) {
				String javaType = field.getFieldType();
				String columnName = getColumnName(field);
				String getter = field.getGetterMethodName();
				String setter = field.getSetterMethodName();

				if (field.isId()) {
					if (field.getIdGenerator() == Field.IdGenerator.AUTO) {
						// don't save PK into DB
					}
					if (field.getIdGenerator() == Field.IdGenerator.UUID) {
						sb.append(TAB2
								+ String.format("String uuid = obj.%s();\n",
										getter));
						sb.append(TAB2 + "if (uuid == null) {\n");
						sb.append(TAB3 + "uuid = generateUUID();\n");
						sb.append(TAB3
								+ String.format("obj.%s(uuid);\n", setter));
						sb.append(TAB2 + "}\n");
						sb.append(TAB2
								+ String.format("v.put(%s, uuid);\n",
										columnName));
					}
				} else {
					if (javaType.equals(Date.class.getName())) {
						// Handle DateTime type
						sb.append(TAB2
								+ String.format(
										"v.put(%s, formatDatetime(obj.%s()));\n",
										columnName, getter));
					} else {
						sb.append(TAB2
								+ String.format("v.put(%s, obj.%s());\n",
										columnName, getter));
					}
				}
			}
		}
		return sb.toString();
	}

	// getPK
	private void genGetPK(StringBuilder sb, Table table) {
		DaoInfo dao = table.getDaoInfo();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("T", table.getEntityClass().className);
		args.put("PK", Utils.upperCaseFirstLetter(getPKFieldName(table)));
		args.put("PKType", dao.getPkClass());
		sb.append(format(FORMAT_GET_PK, args));
	}

	// setPK
	private void genSetPK(StringBuilder sb, Table table) {
		DaoInfo dao = table.getDaoInfo();
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("T", table.getEntityClass().className);
		args.put("PK", Utils.upperCaseFirstLetter(getPKFieldName(table)));
		args.put("PKType", dao.getPkClass());
		sb.append(format(FORMAT_SET_PK, args));
	}

	// getPKClass
	private void genGetPKClass(StringBuilder sb, Table table) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("PKType", getPKFieldType(table) + ".class");
		sb.append(format(FORMAT_GET_PK_CLASS, args));
	}

	// getTableName
	private void genGetTableName(StringBuilder sb, Table table) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("tableName", table.getTableName());
		sb.append(format(FORMAT_GET_TABLE_NAME, args));
	}

	// createTableSql
	private void genGetCreateTableSql(StringBuilder sb, Table table) {
		sb.append(format(FORMAT_GET_CREATE_TABLE_SQL, null));
	}

	// onCreateTable
	private void genOnCreateTable(StringBuilder sb, Table table) {
		sb.append(format(FORMAT_ON_CREATE_TABLE, null));
	}

	// onUpgradeTable
	private void genOnUpgradeTable(StringBuilder sb, Table table) {
		sb.append(format(FORMAT_ON_UPGRADE_TABLE, null));
	}

	// Helper

	private static String getColumnName(Field field) {
		return "COLUMN_" + Utils.toUpperCase(field.getColumnName());
	}

	private static String getColumnIndex(Field field) {
		return "INDEX_" + Utils.toUpperCase(field.getColumnName());
	}

	private static String format(String format, Map<String, Object> args) {
		return TemplateEngine.render(format, args);
	}

	private static String getPKFieldName(Table table) {
		Field pk = table.getPKField();
		String id = "id";
		if (pk != null) {
			id = pk.getColumnName();
		}
		return id;
	}

	private static String getPKFieldType(Table table) {
		Field pk = table.getPKField();
		if (pk != null) {
			return pk.getFieldType();
		}
		return null;
	}

}

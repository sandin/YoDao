package com.yoda.yodao.internal;

import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.JoinColumn;
import com.yoda.yodao.annotation.ManyToMany;
import com.yoda.yodao.annotation.ManyToOne;
import com.yoda.yodao.annotation.NotColumn;
import com.yoda.yodao.annotation.OneToMany;
import com.yoda.yodao.annotation.OneToOne;
import com.yoda.yodao.annotation.Repository;

/**
 * YodaoProcessor
 * 
 * @author lds
 *
 */
public final class YodaoProcessor extends AbstractProcessor {

	private static final String TAG = YodaoProcessor.class.getSimpleName();
	private static final boolean DEBUG = true;

	private static final String DAO_SUFFIX = "DaoImpl";
	private static final String DAO_PACKNAME = ".dao.impl";

	private Elements elementUtils;
	private Types typeUtils;
	private Filer filer;

	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super.init(env);

		elementUtils = env.getElementUtils();
		typeUtils = env.getTypeUtils();
		filer = env.getFiler();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> types = new HashSet<String>();
		types.add(Entity.class.getCanonicalName());
		types.add(Repository.class.getCanonicalName());
		return types;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(Set<? extends TypeElement> elements,
			RoundEnvironment env) {
		List<Table> tables = new ArrayList<Table>();
		for (Element element : env.getElementsAnnotatedWith(Entity.class)) {
			Table table = parseTable(element);
			if (table != null) {
				tables.add(table);
			}
		}

		List<DaoInfo> daos = new ArrayList<DaoInfo>();
		for (Element element : env.getElementsAnnotatedWith(Repository.class)) {
			try {
				DaoInfo dao = RepositoryParser.parser(element);
				daos.add(dao);
				log(TAG, "dao: " + dao);
			} catch (IllegalStateException e) {
				error(element, e.getMessage());
				continue;
			}
		}

		for (DaoInfo dao : daos) {
			for (Table table : tables) {
				Clazz c1 = dao.getEntityClass();
				Clazz c2 = table.getEntityClass();
				if (c1 != null && c2 != null) {
					String n1 = c1.getCanonicalName();
					String n2 = c2.getCanonicalName();
					if (n1 != null && n1.equals(n2)) {
						table.setDaoInfo(dao);
					}
				}
			}
		}

		for (Table table : tables) {
			if (table.getDaoInfo() != null) {
				createSourceFile(filer, table);
			}
		}
		createSourceFile(filer, tables); // for DAO factory
		return true;
	}

	private void createSourceFile(Filer filer, Table table) {
		Element typeElement = table.getElement();
		try {
			String javaContent = table.brewJava();
			Clazz daoClazz = table.getDaoClass();
			JavaFileObject jfo = filer.createSourceFile(daoClazz
					.getCanonicalName());
			Writer writer = jfo.openWriter();
			writer.write(javaContent);
			writer.flush();
			writer.close();
		} catch (IllegalStateException e) {
			error(typeElement, "Unable to generate DAO for entity %s: %s",
					typeElement, e.getMessage());
		} catch (IOException e) {
			error(typeElement, "Unable to generate DAO for entity %s: %s",
					typeElement, e.getMessage());
		} catch (Throwable e) {
			error(typeElement, "Unable to generate DAO for entity %s: %s",
					typeElement, e.getMessage());
		}
	}

	private void createSourceFile(Filer filer, List<Table> tables) {
		try {
			String javaContent = FactoryGenerator.generate(tables);
			if (javaContent != null) {
				JavaFileObject jfo = filer.createSourceFile(FactoryGenerator
						.getCanonicalName());
				Writer writer = jfo.openWriter();
				writer.write(javaContent);
				writer.flush();
				writer.close();
			}
		} catch (IllegalStateException e) {
			error("Unable to generate DaoFactory, %s", e.getMessage());
		} catch (IOException e) {
			error("Unable to generate DaoFactory, %s", e.getMessage());
		} catch (Throwable e) {
			error("Unable to generate DaoFactory, %s", e.getMessage());
		}
	}

	/**
	 * Parse a element to table
	 * 
	 * @param element
	 * @return
	 */
	private Table parseTable(Element element) {
		Table table = new Table();
		table.setElement(element);

		table.setTableName(Utils.toLowerCase(getTableName(element)));
		String className = getTableType(element);
		table.setDaoClass(parseToDaoClazz(className));
		table.setEntityClass(parseToEntityClazz(className));

		List<Field> fields = new ArrayList<Field>();

		List<? extends Element> elements = element.getEnclosedElements();
		for (Element elem : elements) {
			Field field = parseField(elem);
			if (field != null) {
				fields.add(field);
			}
		}
		table.setFields(fields);
		log(TAG, "table: " + table);
		return table;
	}

	/*---------------------------------------------------------------*/

	/**
	 * Parse a element to table's field
	 * 
	 * @param element
	 * @return
	 */
	private Field parseField(Element element) {
		Field field = null;
		if (isTargetField(element)) {
			Column column = element.getAnnotation(Column.class);
			OneToOne oneToOne = element.getAnnotation(OneToOne.class);
			OneToMany oneToMany = element.getAnnotation(OneToMany.class);
			ManyToOne manyToOne = element.getAnnotation(ManyToOne.class);
			ManyToMany manyToMany = element.getAnnotation(ManyToMany.class);

			field = new Field();
			String columnName = getColumnName(element);
			String columnType = getColumnType(element);
			field.setFieldName(element.getSimpleName().toString());
			field.setFieldType(columnType);
			field.setColumnName(columnName);
			field.setColumnType(columnType); // TODO: convert java type to
												// database type

			if (oneToOne != null) {
				parseOneToOne(element, field, oneToOne);
			} else if (oneToMany != null) {
				parseOneToMany(element, field, oneToMany);
			} else if (manyToOne != null) {
				parseManyToOne(element, field, manyToOne);
			} else if (manyToMany != null) {
				parseManyToMany(element, field, manyToMany);
			} else {
				parseColumn(element, field, column);
			}

		}
		log(TAG, "field: " + field);
		return field;
	}

	private void parseColumn(Element element, Field field, Column column) {
		field.setNullable(column != null ? column.nullable() : true);
		field.setIsId(isPKColumn(element));
	}

	private void parseOneToOne(Element element, Field field, OneToOne oneToOne) {
		JoinColumn joinColumn = element.getAnnotation(JoinColumn.class);
		String joinOn = null;
		if (joinColumn != null) {
			joinOn = joinColumn.name();
		}
		if (joinOn == null || joinOn.length() == 0) {
			joinOn = field.getFieldName() + "_id";
		}
		// TODO: OneToOne
		error(element, "Cann't support OneToOne annotation yet.");
	}

	private void parseOneToMany(Element element, Field field,
			OneToMany oneToMany) {
		// TODO: OneToMany
		error(element, "Cann't support OneToMany annotation yet.");
	}

	private void parseManyToOne(Element element, Field field,
			ManyToOne manyToOne) {
		// TODO: ManyToOne
		error(element, "Cann't support ManyToOne annotation yet.");
	}

	private void parseManyToMany(Element element, Field field,
			ManyToMany manyToMany) {
		// TODO: ManyToMany
		error(element, "Cann't support ManyToMany annotation yet.");
	}

	/*---------------------------------------------------------------*/

	private String getTableName(Element element) {
		String tableName = null;
		Entity entity = element.getAnnotation(Entity.class);
		com.yoda.yodao.annotation.Table table = element
				.getAnnotation(com.yoda.yodao.annotation.Table.class);
		if (table != null) {
			tableName = table.name();
		}
		if (tableName == null || tableName.length() == 0) {
			tableName = Utils.toLowerCase(element.getSimpleName().toString());
		}
		return tableName;
	}

	private String getTableType(Element element) {
		if (element != null) {
			return element.asType().toString();
		}
		return null;
	}

	private String getColumnType(Element element) {
		if (element != null) {
			return element.asType().toString();
		}
		return null;
	}

	private String getColumnName(Element element) {
		String columnName = null;
		Column column = element.getAnnotation(Column.class);
		Id id = element.getAnnotation(Id.class);
		if (column != null) {
			columnName = column.name();
		}
		// is id or name is empty
		if (columnName == null || columnName.length() == 0) {
			columnName = Utils.toLowerCase(element.getSimpleName().toString()); // field
																				// name
		}
		return columnName;
	}

	private boolean isPKColumn(Element element) {
		return element.getAnnotation(Id.class) != null;
	}

	public static Clazz parseToEntityClazz(String clazzName) {
		Clazz clazz = new Clazz();
		int index = clazzName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException(
					"model's package name must has at last one `.`");
		}
		clazz.packageName = clazzName.substring(0, index);
		clazz.className = clazzName.substring(index + 1, clazzName.length());
		return clazz;
	}

	public static Clazz parseToDaoClazz(String clazzName) {
		Clazz clazz = new Clazz();
		int index = clazzName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException(
					"model's package name must has at last two `.`");
		}
		clazz.className = clazzName.substring(index + 1, clazzName.length())
				+ DAO_SUFFIX;

		String packageName = clazzName.substring(0, index);
		index = packageName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException(
					"model's package name must has at last two `.`");
		}
		clazz.packageName = packageName.substring(0, index) + DAO_PACKNAME;
		return clazz;
	}

	private boolean isTargetField(Element element) {
		return element != null && element.getKind() == ElementKind.FIELD //
				&& element.getAnnotation(NotColumn.class) == null;
	}

	public void log(String tag, String msg) {
		if (DEBUG) {
			Messager messager = processingEnv.getMessager();
			messager.printMessage(NOTE, String.format("[%s] %s", tag, msg));
		}
	}

	/*-
	public void error(Element element, String tag, String msg) {
		Messager messager = processingEnv.getMessager();
		messager.printMessage(ERROR, String.format("[%s] %s", tag, msg),
				element);
	}
	 -*/

	private void error(Element element, String message, Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}
		processingEnv.getMessager().printMessage(ERROR, message, element);
	}

	private void error(String message, Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}
		processingEnv.getMessager().printMessage(ERROR, message);
	}
}

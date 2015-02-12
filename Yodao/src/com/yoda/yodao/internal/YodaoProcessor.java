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

/**
 * YodaoProcessor
 * 
 * @author lds
 *
 */
public final class YodaoProcessor extends AbstractProcessor {

	private static final String TAG = YodaoProcessor.class.getSimpleName();
	private static final boolean DEBUG = true;
	
	private static final String DAO_SUFFIX = "Dao";
	private static final String DAO_PACKNAME = ".dao";

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
		for (TypeElement te : elements) {
			for (Element element : env.getElementsAnnotatedWith(Entity.class)) {
				Table table = parseTable(element);
				if (table != null) {
					tables.add(table);
				}
			}
		}

		for (Table table : tables) {
			createSourceFile(filer, table);
		}
		return true;
	}

	private void createSourceFile(Filer filer, Table table) {
		Element typeElement = table.getElement();
		try {
			Clazz daoClazz = table.getDaoClass();
			JavaFileObject jfo = filer.createSourceFile(daoClazz.getCanonicalName());
			Writer writer = jfo.openWriter();
			writer.write(table.brewJava());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			error(typeElement, "Unable to write injector for type %s: %s",
					typeElement, e.getMessage());
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
		
		table.setTableName(getTableName(element));
		String className = getTableType(element);
		Clazz clazz = parseToDaoClazz(className);
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

	/**
	 * Parse a element to table's field
	 * 
	 * @param element
	 * @return
	 */
	private Field parseField(Element element) {
		Field field = null;
		if (isTargetField(element)) {
			String columnName = getColumnName(element);
			String columnType = getColumnType(element);
			field = new Field();
			field.setColumnName(columnName);
			field.setColumnType(columnType);
			field.setIsId(isPKColumn(element));
			log(TAG, "field: " + field);
		}
		return field;
	}

	private String getTableName(Element element) {
		String tableName = null;
		Entity entity = element.getAnnotation(Entity.class);
		if (entity != null) {
			tableName = entity.name();
			if (tableName == null || tableName.length() == 0) {
				tableName = element.getSimpleName().toString();
			}
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
		if (column != null || id != null) {
			if (column != null) {
				columnName = column.name();
			}
			if (columnName == null || column.length() == 0) {
				columnName = element.getSimpleName().toString(); // field name
			}
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
		clazz.className = clazzName.substring(index + 1, clazzName.length()) + DAO_SUFFIX;

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
				&& (element.getAnnotation(Id.class) != null || //
				element.getAnnotation(Column.class) != null);
	}

	public void log(String tag, String msg) {
		if (DEBUG) {
			Messager messager = processingEnv.getMessager();
			messager.printMessage(NOTE, String.format("[%s] %s]", tag, msg));
		}
	}

	public void error(Element element, String tag, String msg) {
		Messager messager = processingEnv.getMessager();
		messager.printMessage(ERROR, String.format("[%s] %s", tag, msg),
				element);
	}

	private void error(Element element, String message, Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}
		processingEnv.getMessager().printMessage(ERROR, message, element);
	}

}

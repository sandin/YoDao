package com.yoda.yodao.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import org.junit.Assert;

import com.yoda.yodao.annotation.Repository;
import com.yoda.yodao.internal.query.YoQuery;
import com.yoda.yodao.internal.query.YoQuery.CRUD;

public class RepositoryParser {

	public static DaoInfo parser(Element element) {
		DaoInfo daoInfo = new DaoInfo();
		Repository repository = element.getAnnotation(Repository.class);
		String elementName = element.asType().toString();
		List<? extends TypeMirror> interfaces = ((TypeElement) element)
				.getInterfaces();
		if (interfaces == null || interfaces.size() == 0) {
			throw new IllegalStateException(elementName
					+ " must to be a interface.");
		}
		TypeMirror genericType = Utils.getGenericType(interfaces.get(0));
		if (genericType == null) {
			throw new IllegalStateException(elementName
					+ " must to be a generic type(entity class)");
		}
		daoInfo.setDaoClass(Utils.parseClassNameToClazz(elementName));
		String className = genericType.toString();
		Clazz entityClass = Utils.parseClassNameToClazz(className);
		daoInfo.setEntityClass(entityClass);

		if (!element.getKind().isInterface()) {
			throw new IllegalStateException(elementName
					+ " must to be a interface.");
		}

		daoInfo.setMethods(parserMethods(element));
		return daoInfo;
	}

	private static List<DaoMethod> parserMethods(Element element) {
		List<DaoMethod> methods = new ArrayList<DaoMethod>();
		List<? extends Element> elems = element.getEnclosedElements();
		for (Element e : elems) {
			if (e instanceof ExecutableElement
					&& e.getKind() == ElementKind.METHOD) {
				ExecutableElement executableElement = (ExecutableElement) e;
				TypeElement enclosingElement = (TypeElement) e
						.getEnclosingElement();

				DaoMethod method = new DaoMethod();
				String methodName = executableElement.getSimpleName()
						.toString();
				method.setMethodName(methodName);
				String returnType = executableElement.getReturnType()
						.toString();
				method.setReturnType(returnType);

				List<? extends VariableElement> parameters = executableElement
						.getParameters();
				int count = parameters.size();
				if (parameters != null && count > 0) {
					DaoParam[] params = new DaoParam[count];
					for (int i = 0; i < count; i++) {
						VariableElement param = parameters.get(i);
						String paramType = param.asType().toString();
						String paramName = param.getSimpleName().toString();
						params[i] = new DaoParam(paramName, paramType);
					}
					method.setMethodParams(params);
				}

				methods.add(method);
			}
		}

		return methods;
	}

	private static final String READ_PREFIX = "find";
	private static final String READ_ONE_PREFIX = "findOne";
	private static final String READ_LIST_PREFIX = "findList";
	private static final String CREATE_PREFIX = "save";
	private static final String UPDATE_PREFIX = "save";
	private static final String DELETE_PREFIX = "delete";

	private static final String KEYWORD_BY = "By";
	private static final String KEYWORD_AND = "And";
	private static final String KEYWORD_OR = "Or";
	private static final String KEYWORD_HAVING = "Having";
	private static final String KEYWORD_GROUP_BY = "Group"; // By
	private static final String KEYWORD_ORDER_BY = "Order"; // By

	private static final String[] KEYWORDS = new String[] { KEYWORD_BY,
			KEYWORD_GROUP_BY, KEYWORD_HAVING, KEYWORD_ORDER_BY };

	private static List<String> splitByWord(String name) {
		List<String> word = new ArrayList<String>();
		int length = name.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				word.add(sb.toString());
				sb.setLength(0); // clean
			}
			sb.append(c);
			if (i == length - 1) { // the last one
				word.add(sb.toString());
			}
		}
		return word;
	}

	private static List<String> splitByKeyword(String name) {
		List<String> result = new ArrayList<String>();
		List<String> words = splitByWord(name);
		int size = words.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			String word = words.get(i);
			boolean isKeyword = false;
			if (Arrays.binarySearch(KEYWORDS, word) >= 0) {
				if ((KEYWORD_GROUP_BY.equals(word) || KEYWORD_ORDER_BY
						.equals(word)) && i < size - 1) {
					String nextWord = words.get(i + 1);
					if (KEYWORD_BY.equals(nextWord)) {
						isKeyword = true;
						word += nextWord;
						i++;
					}
				} else {
					isKeyword = true;
				}
			}
			if (isKeyword) {
				result.add(sb.toString());
				sb.setLength(0); // clean
			}
			sb.append(word);
			if (i == size - 1) { // the last one
				result.add(sb.toString());
			}
		}
		return result;

	}

	public static YoQuery parseDaoMethodToQuery(DaoMethod method) {
		YoQuery query = null;
		if (method != null) {
			query = new YoQuery();
			String methodName = method.getMethodName();
			query.setName(methodName);
			List<String> keywords = splitByKeyword(methodName);
			if (keywords.size() > 0) {
				for (int i = 0; i < keywords.size(); i++) {
					String word = keywords.get(i);
					if (i == 0) {
						CRUD crud = handleCrud(word);
						query.setCrud(crud);
					} else {
						// TODO:

					}
				}
			}
		}
		return query;
	}

	private static CRUD handleCrud(String methodName) {
		if (READ_PREFIX.equals(methodName)
				|| READ_ONE_PREFIX.equals(methodName)
				|| READ_LIST_PREFIX.equals(methodName)) {
			return CRUD.READ;
		}
		if (CREATE_PREFIX.equals(methodName)) {
			return CRUD.CREATE;
		}
		if (UPDATE_PREFIX.equals(methodName)) {
			return CRUD.UPDATE;
		}
		if (DELETE_PREFIX.equals(methodName)) {
			return CRUD.DELETE;
		}
		return null;
	}

	public static void main(String[] args) {

		String methodName = "findOneByUsernameAndAge";
		List<String> words = splitByWord(methodName);
		Assert.assertNotNull(words);
		Assert.assertEquals(6, words.size());
		Assert.assertEquals("find", words.get(0));
		Assert.assertEquals("One", words.get(1));
		Assert.assertEquals("By", words.get(2));
		Assert.assertEquals("Username", words.get(3));
		Assert.assertEquals("And", words.get(4));
		Assert.assertEquals("Age", words.get(5));
		System.out.println("words: " + words);

		methodName = "findOneByUsernameAndAgeGroupByUsernameAndAgeOrderByAgeAndUserName";
		methodName = "deleteByUsernameAndAge";
		methodName = "saveByUsernameAndAge";
		words = splitByKeyword(methodName);
		System.out.println("words: " + words);

		methodName = "findOneByUsernameAndAge";
		DaoMethod method = new DaoMethod();
		method.setMethodName(methodName);
		DaoParam[] methodParams = new DaoParam[2];
		methodParams[0] = new DaoParam("username", String.class.getName());
		methodParams[1] = new DaoParam("age", int.class.getName());
		method.setMethodParams(methodParams);
		method.setReturnType("com.test.model.User");

		YoQuery query = parseDaoMethodToQuery(method);
		Assert.assertEquals("findOneByUsernameAndAge", query.getName());
		Assert.assertEquals(CRUD.READ, query.getCrud());
	}

}

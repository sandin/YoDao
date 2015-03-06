package com.yoda.yodao.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import org.junit.Assert;

import com.yoda.yodao.annotation.Repository;
import com.yoda.yodao.internal.query.YoGroupBy;
import com.yoda.yodao.internal.query.YoHaving;
import com.yoda.yodao.internal.query.YoOrderBy;
import com.yoda.yodao.internal.query.YoQuery;
import com.yoda.yodao.internal.query.YoOrderBy.Order;
import com.yoda.yodao.internal.query.YoQuery.CRUD;
import com.yoda.yodao.internal.query.YoSelection;

public class RepositoryParser {

	public static DaoInfo parser(Element element) {
		DaoInfo daoInfo = new DaoInfo();
		Repository repository = element.getAnnotation(Repository.class);
		String elementName = element.asType().toString();

		daoInfo.setDaoClass(Utils.parseClassNameToClazz(elementName));

		boolean isInterface = element.getKind().isInterface();
		daoInfo.setIsInterface(isInterface);
		TypeMirror genericType = null;
		if (isInterface) {
			List<? extends TypeMirror> interfaces = ((TypeElement) element)
					.getInterfaces();
			if (interfaces == null || interfaces.size() == 0) {
				throw new IllegalStateException(elementName
						+ " must to be a interface.");
			}
			genericType = Utils.getGenericType(interfaces.get(0));
		} else {
			TypeMirror superclass = ((TypeElement) element).getSuperclass();
			Utils.getGenericType(superclass);
			Set<Modifier> modifiers = ((TypeElement) element).getModifiers();
			genericType = Utils.getGenericType(superclass);
			if (modifiers.contains(Modifier.ABSTRACT)) {

			}
		}
		if (genericType == null) {
			throw new IllegalStateException(elementName
					+ " must has a generic type which it's the entity class");
		}
		String className = genericType.toString();
		Clazz entityClass = Utils.parseClassNameToClazz(className);
		daoInfo.setEntityClass(entityClass);

		List<DaoMethod> methods = parserMethods(element);
		if (methods != null) {
			for (DaoMethod method : methods) {
				YoQuery query = parseDaoMethodToQuery(method);
				method.setQuery(query);
			}
		}
		daoInfo.setMethods(methods);

		return daoInfo;
	}

	private static boolean isAbstract(ExecutableElement e) {
		return e != null && e.getModifiers().contains(Modifier.ABSTRACT);
	}

	private static List<DaoMethod> parserMethods(Element element) {
		boolean isInterface = element.getKind().isInterface();
		List<DaoMethod> methods = new ArrayList<DaoMethod>();
		List<? extends Element> elems = element.getEnclosedElements();
		for (Element e : elems) {
			if (e instanceof ExecutableElement
					&& e.getKind() == ElementKind.METHOD) {
				ExecutableElement executableElement = (ExecutableElement) e;
				TypeElement enclosingElement = (TypeElement) e
						.getEnclosingElement();
				if (isInterface || isAbstract(executableElement)) {
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
		}

		return methods;
	}

	private static final String READ_PREFIX = "find";
	private static final String READ_ONE_PREFIX = "findOne";
	private static final String READ_LIST_PREFIX = "findList";
	private static final String CREATE_PREFIX = "save";
	private static final String UPDATE_PREFIX = "save";
	private static final String DELETE_PREFIX = "delete";
	private static final String COUNT_PREFIX = "count";

	private static final String KEYWORD_BY = "By";
	private static final String KEYWORD_AND = "And";
	private static final String KEYWORD_OR = "Or";
	private static final String KEYWORD_HAVING = "Having";
	private static final String KEYWORD_GROUP_BY = "Group"; // By
	private static final String KEYWORD_ORDER_BY = "Order"; // By
	private static final String KEYWORD_ORDER_ASC = "Asc";
	private static final String KEYWORD_ORDER_DESC = "Desc";

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

	private static List<String> splitByKeywords(String text, String[] keywords) {
		List<String> result = new ArrayList<String>();
		List<String> words = splitByWord(text);
		int size = words.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			String word = words.get(i);
			boolean isKeyword = false;
			if (Arrays.binarySearch(keywords, word) >= 0) {
				isKeyword = true;
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
				int argIndex = 0;
				for (int i = 0; i < keywords.size(); i++) {
					String word = keywords.get(i);
					if (i == 0) {
						CRUD crud = handleCrud(word);
						query.setCrud(crud);
					} else {
						argIndex = handleKeyword(query, word, argIndex);
					}
				}
			}
		}
		if (query == null || query.getCrud() == null) {
			throw new IllegalStateException("Cann't support method name: " + method.getMethodName());
		}
		return query;
	}

	private static int handleKeyword(YoQuery query, String word, int argIndex) {
		if (word.startsWith(KEYWORD_BY)) {
			return handleWhere(query, word.substring(KEYWORD_BY.length()),
					argIndex);
		} else if (word.startsWith(KEYWORD_ORDER_BY)) {
			return handleOrderBy(query,
					word.substring((KEYWORD_ORDER_BY + KEYWORD_BY).length()),
					argIndex);
		} else if (word.startsWith(KEYWORD_GROUP_BY)) {
			return handleGroupBy(query,
					word.substring((KEYWORD_GROUP_BY + KEYWORD_BY).length()),
					argIndex);
		} else if (word.startsWith(KEYWORD_HAVING)) {
			return handleHaving(query, word.substring(KEYWORD_HAVING.length()),
					argIndex);
		}
		return argIndex;
	}

	private static int handleWhere(YoQuery query, String word, int argIndex) {
		String[] keywords = new String[] { KEYWORD_AND, KEYWORD_OR };
		List<String> words = splitByKeywords(word, keywords);
		if (words != null && words.size() > 0) {
			for (String q : words) {
				YoSelection selection = new YoSelection();
				if (q.startsWith(KEYWORD_AND)) {
					// and
					selection.setField(q.substring(KEYWORD_AND.length()));
					selection.setIsOr(false); // is and
				} else if (q.startsWith(KEYWORD_OR)) {
					// or
					selection.setField(q.substring(KEYWORD_OR.length()));
					selection.setIsOr(true); // is or
				} else {
					// and
					selection.setField(q);
					selection.setIsOr(false); // is and
				}
				selection.setArg(argIndex + "");
				argIndex++;
				query.selection(selection);
			}
		}
		return argIndex;
	}

	private static int handleOrderBy(YoQuery query, String word, int argIndex) {
		String[] keywords = new String[] { KEYWORD_AND };
		List<String> words = splitByKeywords(word, keywords);
		if (words != null && words.size() > 0) {
			for (String q : words) {
				if (q.startsWith(KEYWORD_AND)) {
					q = q.substring(KEYWORD_AND.length());
				}

				YoOrderBy order = new YoOrderBy();
				if (q.endsWith(KEYWORD_ORDER_ASC)) {
					q = q.substring(0, q.length() - KEYWORD_ORDER_ASC.length());
					order.setOrder(Order.ASC);
				} else if (q.endsWith(KEYWORD_ORDER_DESC)) {
					q = q.substring(0, q.length() - KEYWORD_ORDER_DESC.length());
					order.setOrder(Order.DESC);
				} else {
					order.setOrder(Order.ASC); // default
				}
				order.setField(q);
				query.orderBys(order);
			}
		}
		return argIndex;
	}

	private static int handleGroupBy(YoQuery query, String word, int argIndex) {
		String[] keywords = new String[] { KEYWORD_AND };
		List<String> words = splitByKeywords(word, keywords);
		if (words != null && words.size() > 0) {
			for (String q : words) {
				YoGroupBy groupBy = new YoGroupBy(q);
				query.groupBy(groupBy);
			}
		}
		return argIndex;
	}

	private static int handleHaving(YoQuery query, String word, int argIndex) {
		String[] keywords = new String[] { KEYWORD_AND };
		List<String> words = splitByKeywords(word, keywords);
		if (words != null && words.size() > 0) {
			for (String q : words) {
				YoHaving having = new YoHaving(q);
				query.having(having);
			}
		}
		return argIndex;
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
		if (COUNT_PREFIX.equals(methodName)) {
			return CRUD.COUNT;
		}
		return null;
	}

	public static void main(String[] args) {
		String methodName = "findOneByUsernamdeAndAge";
		/*-
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

		methodName = "findOneByUsernameAndAgeOrAgeGroupByUsernameAndAgeHavingUsernameOrderByAgeAscAndUserNameDesc";
		// methodName = "deleteByUsernameAndAge";
		// methodName = "saveByUsernameAndAge";
		words = splitByKeyword(methodName);
		System.out.println("words: " + words);
		-*/

		methodName = "findOneByUsernameAndAgeOrAgeGroupByUsernameAndAgeHavingUsernameOrderByAgeAscAndUsernameDesc";
		DaoMethod method = new DaoMethod();
		method.setMethodName(methodName);
		DaoParam[] methodParams = new DaoParam[2];
		methodParams[0] = new DaoParam("username", String.class.getName());
		methodParams[1] = new DaoParam("age", int.class.getName());
		method.setMethodParams(methodParams);
		method.setReturnType("com.test.model.User");

		YoQuery query = parseDaoMethodToQuery(method);
		System.out.println("query: " + query);
		Assert.assertEquals(methodName, query.getName());
		Assert.assertEquals(CRUD.READ, query.getCrud());

		Assert.assertEquals(3, query.getSelections().size());
		Assert.assertEquals("Username", query.getSelections().get(0).getField());
		Assert.assertEquals("Age", query.getSelections().get(1).getField());
		Assert.assertEquals("Age", query.getSelections().get(2).getField());
		Assert.assertEquals(true, query.getSelections().get(2).isOr());

		Assert.assertEquals(1, query.getHavings().size());
		Assert.assertEquals("Username", query.getHavings().get(0).getField());

		Assert.assertEquals(2, query.getOrderBys().size());
		Assert.assertEquals("Age", query.getOrderBys().get(0).getField());
		Assert.assertEquals(Order.ASC, query.getOrderBys().get(0).getOrder());
		Assert.assertEquals("Username", query.getOrderBys().get(1).getField());
		Assert.assertEquals(Order.DESC, query.getOrderBys().get(1).getOrder());
	}

}

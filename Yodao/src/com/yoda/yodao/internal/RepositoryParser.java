package com.yoda.yodao.internal;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import com.yoda.yodao.annotation.Repository;

public class RepositoryParser {

	public static DaoInfo parser(Element element) {
		DaoInfo daoInfo = new DaoInfo();
		Repository repository = element.getAnnotation(Repository.class);
		String elementName = element.asType().toString();
		List<? extends TypeMirror> interfaces = ((TypeElement) element)
				.getInterfaces();
		if (interfaces == null || interfaces.size() == 0) {
			return null;
		}
		TypeMirror genericType = Utils.getGenericType(interfaces.get(0));
		//log(TAG, "genericType: " + genericType.toString());
		if (genericType == null) {
			return null;
		}
		daoInfo.setDaoClass(Utils.parseClassNameToClazz(elementName));
		String className = genericType.toString();
		Clazz entityClass = Utils.parseClassNameToClazz(className);
		daoInfo.setEntityClass(entityClass);
		List<DaoMethod> methods = new ArrayList<DaoMethod>();

		if (element.getKind().isInterface()) {
			List<? extends Element> elems = element.getEnclosedElements();
			for (Element e : elems) {
				if (e.getKind() == ElementKind.METHOD) {
					String methodName = e.getSimpleName().toString();
					//log(TAG, "methodName: " + methodName);
					DaoMethod method = new DaoMethod();
					method.setMethodName(methodName);
					// TODO: args
					methods.add(method);
				}
			}
		} else {
			throw new IllegalStateException(elementName + " must to be a interface.");
		}
		
		daoInfo.setMethods(methods);
		return daoInfo;
	}

}

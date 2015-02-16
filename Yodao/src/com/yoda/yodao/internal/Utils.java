package com.yoda.yodao.internal;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor6;

import org.junit.Assert;

public class Utils {

	/**
	 * First letter to upperCase
	 * 
	 * @param text
	 * @return
	 */
	public static String upperCaseFirstLetter(String text) {
		if (text != null && text.length() > 0) {
			return String.valueOf(text.charAt(0)).toUpperCase()
					+ text.substring(1);
		}
		return text;
	}

	/**
	 * First letter to upperCase
	 * 
	 * @param text
	 * @return
	 */
	public static String lowerCaseFirstLetter(String text) {
		if (text != null && text.length() > 0) {
			return String.valueOf(text.charAt(0)).toLowerCase()
					+ text.substring(1);
		}
		return text;
	}

	/**
	 * to upper case like XXX_XXX_XXX
	 * 
	 * @param text
	 * @return
	 */
	public static String toUpperCase(String text) {
		if (text != null && text.length() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (i != 0 && Character.isUpperCase(c)) {
					sb.append("_");
				}
				sb.append(Character.toUpperCase(c));
			}
			return sb.toString();
		}
		return text;
	}

	/**
	 * to upper case like xxx_xxx_xxx
	 * 
	 * @param text
	 * @return
	 */
	public static String toLowerCase(String text) {
		if (text != null && text.length() > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < text.length(); i++) {
				char c = text.charAt(i);
				if (i != 0 && Character.isUpperCase(c)) {
					sb.append("_");
				}
				sb.append(Character.toLowerCase(c));
			}

			return sb.toString();
		}
		return text;
	}

	public static void main(String[] args) {
		String text = "userName";
		Assert.assertEquals("user_name", toLowerCase(text));
		Assert.assertEquals("USER_NAME", toUpperCase(text));

		Assert.assertEquals("user", toLowerCase("user"));
		Assert.assertEquals("user", toLowerCase("User"));

	}

	public static PackageElement getPackage(Element type) {
		while (type.getKind() != ElementKind.PACKAGE) {
			type = type.getEnclosingElement();
		}
		return (PackageElement) type;
	}

	public static TypeMirror getGenericType(final TypeMirror type) {
		final TypeMirror[] result = { null };

		type.accept(new SimpleTypeVisitor6<Void, Void>() {
			
			@Override
			public Void visitDeclared(DeclaredType declaredType, Void v) {
				List<? extends TypeMirror> typeArguments = declaredType
						.getTypeArguments();
				if (!typeArguments.isEmpty()) {
					result[0] = typeArguments.get(0);
				}
				return null;
			}

			@Override
			public Void visitPrimitive(PrimitiveType primitiveType, Void v) {
				return null;
			}

			@Override
			public Void visitArray(ArrayType arrayType, Void v) {
				return null;
			}

			@Override
			public Void visitTypeVariable(TypeVariable typeVariable, Void v) {
				return null;
			}

			@Override
			public Void visitError(ErrorType errorType, Void v) {
				return null;
			}

			@Override
			protected Void defaultAction(TypeMirror typeMirror, Void v) {
				throw new UnsupportedOperationException();
			}
		}, null);

		return result[0];
	}
	
	public static Clazz parseClassNameToClazz(String clazzName) {
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

}

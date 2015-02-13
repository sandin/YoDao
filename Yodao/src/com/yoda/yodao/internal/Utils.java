package com.yoda.yodao.internal;

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
}

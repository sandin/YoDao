package com.yoda.yodao.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;


public class TemplateEngine {
	
	public static String render(String format, Map<String, Object> args) {
		if (format != null) {
			if (args != null) {
				Iterator<String> it = args.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					Object value = args.get(key);
					key = "::" + key + "::";
					System.out.println("key: " + key);
					if (format.contains(key)) {
						format = format.replaceAll(key, value.toString());
					}
				}
			}
		}
		return format;
	}
	
	public static void main(String[] args) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("T", "User");
		Assert.assertEquals("public User() {}", render("public ::T::() {}", maps));
		
	}

}

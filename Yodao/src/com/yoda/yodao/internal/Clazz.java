package com.yoda.yodao.internal;

public class Clazz {

	public String className;

	public String packageName;
	
	public String getCanonicalName() {
		return packageName + "." + className;
	}

	@Override
	public String toString() {
		return "Clazz [className=" + className + ", packageName=" + packageName
				+ "]";
	}

}

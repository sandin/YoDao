package com.yoda.yodao.internal;

import java.util.Arrays;

public class DaoMethod {

	private String methodName;

	private Object[] methodArgs;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getMethodArgs() {
		return methodArgs;
	}

	public void setMethodArgs(Object[] methodArgs) {
		this.methodArgs = methodArgs;
	}

	@Override
	public String toString() {
		return "DaoMethod [methodName=" + methodName + ", methodArgs="
				+ Arrays.toString(methodArgs) + "]";
	}

}

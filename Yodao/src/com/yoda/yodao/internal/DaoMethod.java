package com.yoda.yodao.internal;

import java.util.Arrays;

import com.yoda.yodao.internal.query.YoQuery;

public class DaoMethod {

	private String methodName;

	private DaoParam[] methodParams;

	private String returnType;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public DaoParam[] getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(DaoParam[] methodParams) {
		this.methodParams = methodParams;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	public YoQuery toQuery() {
		return RepositoryParser.parseDaoMethodToQuery(this);
	}

	@Override
	public String toString() {
		return "DaoMethod [methodName=" + methodName + ", methodArgs="
				+ Arrays.toString(methodParams) + ", returnType=" + returnType
				+ "]";
	}

}

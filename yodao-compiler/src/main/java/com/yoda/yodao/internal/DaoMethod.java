package com.yoda.yodao.internal;

import java.util.Arrays;

import com.yoda.yodao.internal.query.YoQuery;

import javax.lang.model.element.Element;

public class DaoMethod {

	private String methodName;

	private DaoParam[] methodParams;

	private String returnType;

	private String sql;

	private YoQuery query;

	private Element element;

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

	public YoQuery toQuery() throws ProcessException {
		return RepositoryParser.parseDaoMethodToQuery(this);
	}

	public YoQuery getQuery() {
		return query;
	}

	public void setQuery(YoQuery query) {
		this.query = query;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
	public String toString() {
		return "DaoMethod [methodName=" + methodName + ", methodParams="
				+ Arrays.toString(methodParams) + ", returnType=" + returnType
				+ ", sql=" + sql + ", query=" + query + "]";
	}

}

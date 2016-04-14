package com.yoda.yodao.internal;

import javax.lang.model.element.Element;

/**
 * Created by lds on 2016/4/14.
 */
public class ProcessException extends Exception {

    private Element mElement;

    public ProcessException(Element element, String message, Throwable cause) {
        super(message, cause);
        mElement = element;
    }

    public ProcessException(Element element, String message) {
        this(element, message, null);
    }

    public Element getElement() {
        return mElement;
    }
}

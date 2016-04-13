package com.yoda.yodao.internal.test;

import com.yoda.yodao.internal.Clazz;
import com.yoda.yodao.internal.YodaoProcessor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lds on 2016/4/13.
 */
public class YodaoProcessorTest {

    @Test
    public void testParseToDaoClazz() {
        Clazz clazz = YodaoProcessor.parseToDaoClazz("com.lds.model.User");
        Assert.assertEquals("com.lds.dao.impl", clazz.packageName);
        Assert.assertEquals("UserDaoImpl", clazz.className);
        System.out.println("class: " + clazz.className);
        System.out.println("package: " + clazz.packageName);
    }

    @Test
    public void testParseToEntityClazz() {
        Clazz clazz = YodaoProcessor.parseToEntityClazz("com.lds.model.User");
        Assert.assertEquals("com.lds.model", clazz.packageName);
        Assert.assertEquals("User", clazz.className);
        System.out.println("class: " + clazz.className);
        System.out.println("package: " + clazz.packageName);
    }
}

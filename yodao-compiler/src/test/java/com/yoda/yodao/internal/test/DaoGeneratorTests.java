package com.yoda.yodao.internal.test;

import com.yoda.yodao.internal.Clazz;
import com.yoda.yodao.internal.DaoGenerator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lds on 2016/4/13.
 */
public class DaoGeneratorTests {

    @Test
    public void testParseToDaoClazz() {
        Assert.assertEquals("Long", DaoGenerator.mapJavaTypeToCursorType("long"));
        System.out.println(DaoGenerator.mapJavaTypeToCursorType("long"));

    }
}

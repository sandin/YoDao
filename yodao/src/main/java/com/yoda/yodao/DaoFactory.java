package com.yoda.yodao;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

//import com.yoda.yodao.internal.Clazz;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yoda.yodao.annotation.Repository;

/**
 * Factory of DAOs
 *
 * @author lds
 */
public final class DaoFactory {

    private static final String SUFFIX = "Impl";

    private static Map<String, SoftReference<Class<?>>> mClazzCache = new HashMap<>();

    public static void onCreateTable(SQLiteDatabase db) {
        // create(HairDao.class).onCreateTable(db);
        // create(PhotoDao.class).onCreateTable(db);
    }

    public static void onUpgradeTable(SQLiteDatabase db, int oldVersion,
                                      int newVersion) {
        // create(HairDao.class).onUpgradeTable(db, oldVersion, newVersion);
        // create(PhotoDao.class).onUpgradeTable(db, oldVersion, newVersion);
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> daoClass, SQLiteOpenHelper openHelper) {
        String daoClassName = daoClass.getCanonicalName();
        String[] cls = parseToClazz(daoClassName);
        daoClassName = cls[0] + ".impl." + cls[1] + SUFFIX;
        try {
            Class<?> clazz = null;
            SoftReference<Class<?>> ref = mClazzCache.get(daoClassName);
            if (ref != null) {
                clazz = ref.get();
            }
            if (clazz == null) {
                clazz = Class.forName(daoClassName);
                mClazzCache.put(daoClassName, new SoftReference<Class<?>>(clazz));
            }

            if (openHelper != null) {
                Constructor<?> constructor = clazz
                        .getConstructor(SQLiteOpenHelper.class);
                return (T) constructor.newInstance(openHelper);
            } else {
                return (T) clazz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(daoClassName + " cann't found.");
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(daoClassName + " cann't found.");
        } catch (SecurityException e) {
            throw new IllegalArgumentException(daoClassName + " cann't found.");
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(daoClassName + " cann't found.");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(daoClassName + " cann't found.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(daoClassName + " cann't found.");
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(daoClassName + " cann't found.");
        }
    }

    public static String[] parseToClazz(String clazzName) {
        int index = clazzName.lastIndexOf(".");
        if (index == -1) {
            throw new IllegalArgumentException(
                    "model's package name must has at last one `.`");
        }
        String packageName = clazzName.substring(0, index);
        String className = clazzName.substring(index + 1, clazzName.length());
        return new String[] { packageName, className };
    }

}

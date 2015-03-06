package com.yoda.yodao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.yoda.yodao.internal.Clazz;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Factory of DAOs
 * 
 * @author lds
 *
 */
public final class DaoFactory {

	private static final String SUFFIX = "Impl";

	public static void onCreateTable(SQLiteDatabase db) {
		// create(HairDao.class).onCreateTable(db);
		// create(PhotoDao.class).onCreateTable(db);
	}

	public static void onUpgradeTable(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		// create(HairDao.class).onUpgradeTable(db, oldVersion, newVersion);
		// create(PhotoDao.class).onUpgradeTable(db, oldVersion, newVersion);
	}

	public static <T> T create(Class<T> daoClass) {
		return create(daoClass, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> daoClass, SQLiteOpenHelper openHelper) {
		String daoClassName = daoClass.getCanonicalName();
		Clazz cls = parseToClazz(daoClassName);
		daoClassName = cls.packageName + ".impl." + cls.className + SUFFIX;
		try {
			Class<?> clazz = Class.forName(daoClassName);
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

	public static Clazz parseToClazz(String clazzName) {
		Clazz clazz = new Clazz();
		int index = clazzName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException(
					"model's package name must has at last one `.`");
		}
		clazz.packageName = clazzName.substring(0, index);
		clazz.className = clazzName.substring(index + 1, clazzName.length());
		return clazz;
	}

}

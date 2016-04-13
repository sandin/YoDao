package com.yoda.yodao;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Base DAO
 * 
 * @author lds
 *
 * @param <T>
 *            entity of DAO
 * @param <ID>
 *            PK
 */
public abstract class BaseDao<T, ID extends Serializable> implements
		YoDao<T, ID> {
	private static final String TAG = YoDao.TAG;
	private static boolean DEBUG = true;

	/**
	 * Android database open helper
	 */
	protected SQLiteOpenHelper mOpenHelper;

	/**
	 * table name of this DAO
	 */
	protected String mTableName = null;

	/**
	 * PK
	 */
	protected String mPrimaryKey = BaseColumns._ID;

	/**
	 * Constructor
	 * 
	 * @param openHelper
	 */
	public BaseDao(SQLiteOpenHelper openHelper) {
		mOpenHelper = openHelper;
	}

	/*-------------------------- Abstract ---------------------------*/

	/**
	 * Get the table name for this DAO
	 * 
	 * @return
	 */
	public abstract String getTableName();

	/**
	 * set PK for entity
	 * 
	 * @param entity
	 * @param id
	 * @return
	 */
	public abstract T setPK(T entity, ID id);

	/**
	 * get PK of entity
	 * 
	 * @param entity
	 * @return
	 */
	public abstract ID getPK(T entity);

	/**
	 * Parse cursor to object
	 * 
	 * @param cursor
	 *            请不要手动关闭它,Cursor可能装的是个list
	 * @return
	 */
	public abstract T cursorToObject(Cursor cursor, String[] columns);

	/**
	 * Convert object to ContentValues for insert/update it into the database
	 * 
	 * @param row
	 * @return
	 */
	public abstract ContentValues objectToValues(T row);

	/**
	 * Get Create Database Table SQL as String
	 */
	public abstract String getCreateTableSql();

	public abstract Class<?> getPKClass();

	/*-------------------------- API ---------------------------*/

	@Override
	public boolean save(T entity) {
		log("save entity: " + entity);
		SQLiteDatabase db = getDb(true);
		return save(db, entity);
	}

	protected boolean save(SQLiteDatabase db, T entity) {
		boolean successed = false;
		if (entity != null) {
			if (isEmptyEntity(entity)) { // for insert
				long rowId = db
						.insert(mTableName, null, objectToValues(entity));
				successed = rowId > 0;
				if (isPkEqualsRowId()) {
					setPK(entity, (ID) (Long) rowId);
				}
			} else { // for update
				long count = update(entity);
				successed = count > 0;
			}
		}
		return successed;
	}

	@Override
	public boolean save(List<T> entities) {
		log("save entities: " + entities);
		boolean allSuccessed = true;
		SQLiteDatabase db = getDb(true);
		db.beginTransaction();
		try {
			allSuccessed = saveWithoutTransaction(db, entities);
			db.setTransactionSuccessful();
		} catch (Throwable e) {
			e.printStackTrace();
			allSuccessed = false;
		} finally {
			db.endTransaction();
		}
		return allSuccessed;
	}

	protected boolean saveWithoutTransaction(SQLiteDatabase db, List<T> entities) {
		boolean allSuccessed = true;
		for (T entity : entities) {
			boolean successed = save(db, entity);
			if (!successed) {
				allSuccessed = false;
			}
		}
		return allSuccessed;
	}

	private boolean isEmptyEntity(T entity) {
		ID id = getPK(entity);
		Class<?> clazz = getPKClass();
		if (!clazz.isPrimitive()) {
			return id == null;
		}
		if (id instanceof Number) {
			return ((Number) id).longValue() == 0L;
		}

		throw new IllegalArgumentException("Unsupport PK type: " + clazz
				+ ", id=" + id);
	}

	@Override
	public int update(T entity) {
		ID id = getPK(entity);
		return updateByFields(objectToValues(entity), whereClauseByPK(),
				whereArgsByPK(id));
	}

	@Override
	public int updateByFields(T entity, String whereClause, String[] whereArgs) {
		return updateByFields(objectToValues(entity), whereClause, whereArgs);
	}

	@Override
	public int updateByFields(ContentValues values, String whereClause,
			String[] whereArgs) {
		SQLiteDatabase db = getDb(true);
		return db.update(mTableName, values, whereClause, whereArgs);
	}

	private boolean isPkEqualsRowId() {
		Class<?> clazz = getPKClass();
		if (clazz == Long.class) {
			return true;
		}
		if (clazz == long.class) {
			return true;
		}
		if (clazz == Integer.class) {
			return true;
		}
		if (clazz == int.class) {
			return true;
		}
		return false;
	}

	@Override
	public T findOne(ID id) {
		return findOneByFields(whereClauseByPK(), whereArgsByPK(id), null);
	}

	@Override
	public T findOneByFields(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		log("find one by fields: where %s, args %s, group by %s, having %s, order by %s",
				selection, Arrays.toString(selectionArgs), groupBy, having,
				orderBy);
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.query(mTableName, null, selection, selectionArgs,
				groupBy, having, orderBy);
		return _cursorToObject(cursor);
	}

	@Override
	public T findOneByFields(String selection, String[] selectionArgs,
			String orderBy) {
		return findOneByFields(selection, selectionArgs, null, null, orderBy);
	}

	@Override
	public T findOneBySql(String sql, String[] selectionArgs) {
		log("find one by sql: %s", sql);
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		return _cursorToObject(cursor);
	}

	@Override
	public List<T> findAll() {
		return findListByFields(null, null, null);
	}

	@Override
	public List<T> findListByFields(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		log("find list by fields: where %s, args %s, group by %s, having %s, order by %s",
				selection, Arrays.toString(selectionArgs), groupBy, having,
				orderBy);
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.query(mTableName, null, selection, selectionArgs,
				groupBy, having, orderBy);
		List<T> list = cursorToList(cursor, null);
		cursor.close();
		return list;
	}

	@Override
	public List<T> findListByFields(String selection, String[] selectionArgs,
			String orderBy) {
		return findListByFields(selection, selectionArgs, null, null, orderBy);
	}

	@Override
	public List<T> findListBySql(String sql, String[] selectionArgs) {
		log("find list by sql: %s", sql);
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		List<T> list = cursorToList(cursor, null);
		cursor.close();
		return list;
	}

	@Override
	public boolean exists(ID id) {
		return countByFields(whereClauseByPK(), whereArgsByPK(id)) != 0;
	}

	@Override
	public long count() {
		return countByFields(null, null);
	}

	@Override
	public int delete(ID id) {
		return deleteByFields(whereClauseByPK(), whereArgsByPK(id));
	}

	@Override
	public int delete(T entity) {
		int count = 0;
		if (entity != null) {
			ID id = getPK(entity);
			if (id != null) {
				count = delete(id);
			}
		}
		return count;
	}

	@Override
	public int delete(List<T> entities) {
		int count = 0;
		SQLiteDatabase db = getDb(true);
		db.beginTransaction();
		try {
			for (T item : entities) {
				count += delete(item);
			}
			db.setTransactionSuccessful();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return count;
	}

	@Override
	public int deleteAll() {
		return deleteByFields(null, null);
	}

	@Override
	public int deleteByFields(String selection, String[] selectionArgs) {
		log("delete by fields: where %s, args %s", selection,
				Arrays.toString(selectionArgs));
		SQLiteDatabase db = getDb(true);
		return db.delete(mTableName, selection, selectionArgs);
	}

	@Override
	public void debug() {
		DEBUG = true;
	}

	/*----------------------------------------------*/

	/**
	 * 打开数据库
	 * 
	 * @param writeable
	 *            true为读写模式，false为只读
	 * @return
	 */
	@Override
	public SQLiteDatabase getDb(boolean writeable) {
		if (writeable) {
			return mOpenHelper.getWritableDatabase();
		} else {
			return mOpenHelper.getReadableDatabase();
		}
	}

	@Override
	public long countByFields(String selections, String[] selectionArgs) {
		log("count by fields: where %s, args %s", selections,
				Arrays.toString(selectionArgs));
		SQLiteDatabase db = getDb(false);
		long count = db.query(mTableName, null, selections, selectionArgs,
				null, null, null).getCount();
		return count;
	}

	protected final void log(String format, Object... args) {
		String msg = "[" + mTableName + "] ";
		if (args.length > 1) {
			msg += String.format(format, args);
		} else {
			msg += format;
		}
		if (DEBUG) {
			Log.v(TAG, msg);
		}
	}

	protected final String whereClauseByPK() {
		return mPrimaryKey + " = ?";
	}

	protected final String[] whereArgsByPK(ID id) {
		return new String[] { String.valueOf(id) };
	}

	protected final T _cursorToObject(Cursor cursor) {
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			T obj = cursorToObject(cursor, null);
			cursor.close();
			return obj;
		}
		return null;
	}

	/**
	 * Convert cursor object to list
	 * 
	 * @param cursor
	 * @return
	 */
	public final List<T> cursorToList(Cursor cursor, String[] columns) {
		List<T> list = null;
		if (cursor != null && cursor.getCount() > 0) {
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(cursorToObject(cursor, columns));
			}
		}
		return list;
	}

	public void setPrimaryKey(String pk) {
		mPrimaryKey = pk;
	}

	public String getPrimaryKey() {
		return mPrimaryKey;
	}

	protected long findLongColBySql(String sql, String[] selectionArgs) {
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		long col = -1;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			if (cursor.getColumnCount() > 0) {
				col = cursor.getLong(0);
			}
			cursor.close();
		}
		return col;
	}

	protected int findIntColBySql(String sql, String[] selectionArgs) {
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		int col = -1;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			if (cursor.getColumnCount() > 0) {
				col = cursor.getInt(0);
			}
			cursor.close();
		}
		return col;
	}

	protected String findStringColBySql(String sql, String[] selectionArgs) {
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.rawQuery(sql, selectionArgs);
		String col = null;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			if (cursor.getColumnCount() > 0) {
				col = cursor.getString(0);
			}
			cursor.close();
		}
		return col;
	}

	/*---------------------------- Helper ------------------------------*/

	protected boolean parseBoolean(int value) {
		return value != 0;
	}

	protected Date parseDatetime(String datetime) {
		if (datetime != null && datetime.length() > 0) {
			try {
				return getDefaultDateFormat().parse(datetime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected String formatDatetime(Date date) {
		if (date != null) {
			try {
				return getDefaultDateFormat().format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected static final String AGO_FULL_DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

	protected static ThreadLocal<DateFormat> TL_AGO_FULL_DATE_FORMATTER = new ThreadLocal<DateFormat>();

	protected DateFormat getDefaultDateFormat() {
		return getDataFormatThreadSafe(TL_AGO_FULL_DATE_FORMATTER,
				AGO_FULL_DATE_FORMATTER);
	}

	protected static DateFormat getDataFormatThreadSafe(
			ThreadLocal<DateFormat> threadLocal, String format) {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<DateFormat>();
		}
		DateFormat df = threadLocal.get();
		if (df == null) {
			df = new SimpleDateFormat(format, Locale.US);
			threadLocal.set(df);
		}
		return df;
	}

	/**
	 * generate a new UUID
	 * 
	 * @return 32位的uuid
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}

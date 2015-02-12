package com.yoda.yodao;

import java.util.ArrayList;
import java.util.List;

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
public abstract class BaseDao<T> implements IDao<T> {
	private static final String TAG = YoDao.TAG;
	private static boolean DEBUG = YoDao.DEBUG;;

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
	public abstract T setPK(T entity, long id);

	/**
	 * get PK of entity
	 * 
	 * @param entity
	 * @return
	 */
	public abstract long getPK(T entity);

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
	
	/*-------------------------- API ---------------------------*/

	@Override
	public T save(T entity) {
		log("save entity: " + entity);
		SQLiteDatabase db = getDb(true);
		entity = save(db, entity);
		return entity;
	}

	protected T save(SQLiteDatabase db, T entity) {
		if (entity != null) {
			long id = getPK(entity);
			if (id <= 0) { // for insert
				id = db.insert(mTableName, null, objectToValues(entity));
				setPK(entity, id);
			} else { // for update
				db.update(mTableName, objectToValues(entity),
						whereClauseByPK(), whereArgsByPK(id));
			}
		}
		return entity;
	}

	@Override
	public List<T> save(List<T> entities) {
		log("save entities: " + entities);
		SQLiteDatabase db = getDb(true);
		db.beginTransaction();
		try {
			for (T entity : entities) {
				save(db, entity);
			}
			db.setTransactionSuccessful();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return entities;
	}

	@Override
	public T findOne(long id) {
		return findOneByFields(whereClauseByPK(), whereArgsByPK(id), null);
	}

	@Override
	public T findOneByFields(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		log("find one by fields: where %s, args %s, group by %s, having %s, order by %s",
				selection, selectionArgs, groupBy, having, orderBy);
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.query(mTableName, null, selection, selectionArgs,
				groupBy, having, orderBy);
		return cursorToObject(cursor);
	}

	@Override
	public T findOneByFields(String selection, String[] selectionArgs,
			String orderBy) {
		return findOneByFields(selection, selectionArgs, null, null, orderBy);
	}

	@Override
	public T findOneBySql(String sql) {
		log("find one by sql: %s", sql);
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.rawQuery(sql, null);
		return cursorToObject(cursor);
	}

	@Override
	public boolean exists(long id) {
		return countByFields(whereClauseByPK(), whereArgsByPK(id)) != 0;
	}

	@Override
	public List<T> findAll() {
		return findListByFields(null, null, null);
	}

	@Override
	public List<T> findListByFields(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		log("find list by fields: where %s, args %s, group by %s, having %s, order by %s",
				selection, selectionArgs, groupBy, having, orderBy);
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
	public List<T> findListBySql(String sql) {
		log("find list by sql: %s", sql);
		SQLiteDatabase db = getDb(false);
		Cursor cursor = db.rawQuery(sql, null);
		List<T> list = cursorToList(cursor, null);
		cursor.close();
		return list;
	}

	@Override
	public long count() {
		return countByFields(null, null);
	}

	@Override
	public int delete(long id) {
		return deleteByFields(whereClauseByPK(), whereArgsByPK(id));
	}

	@Override
	public int delete(T entity) {
		int count = 0;
		if (entity != null) {
			long id = getPK(entity);
			if (id > 0) {
				count = delete(id); 
			}
		}
		return count;
	}

	@Override
	public int delete(List<T> entities) {
		SQLiteDatabase db = getDb(true);
		db.beginTransaction();
		try {
			for (T item : entities) {
				delete(item);
			}
			db.setTransactionSuccessful();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return 0;
	}

	@Override
	public int deleteAll() {
		return deleteByFields(null, null);
	}

	@Override
	public int deleteByFields(String selection, String[] selectionArgs) {
		log("delete by fields: where %s, args %s", selection, selectionArgs);
		SQLiteDatabase db = getDb(true);
		return db.delete(mTableName, selection, selectionArgs);
	}

	/*----------------------------------------------*/

	/**
	 * 打开数据库
	 * 
	 * @param writeable
	 *            true为读写模式，false为只读
	 * @return
	 */
	protected SQLiteDatabase getDb(boolean writeable) {
		if (writeable) {
			return mOpenHelper.getWritableDatabase();
		} else {
			return mOpenHelper.getReadableDatabase();
		}
	}

	protected long countByFields(String selections, String[] selectionArgs) {
		log("count by fields: where %s, args %s", selections, selectionArgs);
		SQLiteDatabase db = getDb(false);
		long count = db.query(mTableName, null, selections, selectionArgs,
				null, null, null).getCount();
		return count;
	}

	protected void log(String format, Object... args) {
		String msg = "[" + mTableName + "] " + format;
		if (args.length > 1) {
			msg = String.format(format, args);
		}
		if (DEBUG) {
			Log.v(TAG, msg);
		}
	}

	protected String whereClauseByPK() {
		return mPrimaryKey + " = ?";
	}

	protected String[] whereArgsByPK(long id) {
		return new String[] { String.valueOf(id) };
	}

	protected T cursorToObject(Cursor cursor) {
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
	public List<T> cursorToList(Cursor cursor, String[] columns) {
		List<T> list = null;
		if (cursor != null && cursor.getCount() > 0) {
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(cursorToObject(cursor, columns));
			}
		}
		return list;
	}

}

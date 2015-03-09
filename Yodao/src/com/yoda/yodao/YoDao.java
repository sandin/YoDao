package com.yoda.yodao;

import java.io.Serializable;
import java.util.List;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Interface DAO
 * 
 * @author lds
 *
 * @param <T>
 *            entity of DAO
 * @param <ID>
 *            PK
 */
public interface YoDao<T, ID extends Serializable> {
	
	public static final String TAG = YoDao.class.getSimpleName();

	/**
	 * Saves a given entity. Use the returned instance for further operations as
	 * the save operation might have changed the entity instance completely.
	 *
	 * @param entity
	 * @return the saved entity
	 */
	boolean save(T entity);

	/**
	 * Saves all given entities.
	 *
	 * @param entities
	 * @return
	 */
	boolean save(List<T> entities);
	
	/**
     * update a row by primary key
     * 
     * @param id
     * @param values
     * @return
     */
	int update(T entity);
	
	/**
	 * update by fields
	 * 
	 * @param entity
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	int updateByFields(T entity, String whereClause,
            String[] whereArgs);

	/**
	 * update some fields
	 * 
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	int updateByFields(ContentValues values, String whereClause,
            String[] whereArgs);

	/**
	 * Retrives an entity by its primary key.
	 *
	 * @param id
	 * @return the entity with the given primary key or {@code null} if none
	 *         found
	 * @throws IllegalArgumentException
	 *             if primaryKey is {@code null}
	 */
	T findOne(ID id);

	/**
	 * Retrives an entity by its fields
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	T findOneByFields(String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy);

	/**
	 * Retrives an entity by its fields
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @param orderBy
	 * @return
	 */
	T findOneByFields(String selection, String[] selectionArgs, String orderBy);

	/**
	 * Retrives an entity by sql
	 * 
	 * @param sql
	 * @return
	 */
	T findOneBySql(String sql, String[] selectionArgs);

	

	/**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id
	 * @return true if an entity with the given id exists, alse otherwise
	 * @throws IllegalArgumentException
	 *             if primaryKey is {@code null}
	 */
	boolean exists(ID id);

	/**
	 * Returns all instances of the type.
	 *
	 * @return all entities
	 */
	List<T> findAll();

	/**
	 * Find List By Fields
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return
	 */
	List<T> findListByFields(String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy);

	/**
	 * Find List By Fields
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @param orderBy
	 * @return
	 */
	List<T> findListByFields(String selection, String[] selectionArgs,
			String orderBy);

	/**
	 * Find List By SQL
	 * 
	 * @param sql
	 * @return
	 */
	List<T> findListBySql(String sql, String[] selectionArgs);

	/**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities
	 */
	long count();

	/**
	 * Returns the number of entities with selections available.
	 * 
	 * @param selections
	 * @param selectionArgs
	 * @return
	 */
	long countByFields(String selections, String[] selectionArgs);

	/**
	 * Deletes the entity with the given id.
	 * 
	 * @param id
	 */
	int delete(ID id);

	/**
	 * Deletes a given entity.
	 *
	 * @param entity
	 */
	int delete(T entity);

	/**
	 * Deletes the given entities.
	 *
	 * @param entities
	 */
	int delete(List<T> entities);

	/**
	 * Deletes all entities managed by the repository.
	 */
	int deleteAll();

	/**
	 * Deletes entities by fields
	 * 
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	int deleteByFields(String selection, String[] selectionArgs);

	/**
	 * Get database instance
	 * 
	 * @param writeable
	 * @return
	 */
	SQLiteDatabase getDb(boolean writeable);
	
	/**
	 * Get create database SQL
	 * 
	 * @return
	 */
	String getCreateTableSql();
	
	/**
	 * Create Table
	 * 
	 * @see SQLiteOpenHelper#onCreate(SQLiteDatabase)
	 * @param db
	 */
	void onCreateTable(SQLiteDatabase db);
	
	/**
	 * Upgrade table
	 * 
	 * @see SQLiteOpenHelper#onUpgrade(SQLiteDatabase, int, int)
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	void onUpgradeTable(SQLiteDatabase db, int oldVersion, int newVersion);
	
	/**
	 * DEBUG or not
	 */
	void debug();

}

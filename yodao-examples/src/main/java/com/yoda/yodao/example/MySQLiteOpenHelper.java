package com.yoda.yodao.example;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yoda.yodao.DaoFactory;
import com.yoda.yodao.example.dao.HairDao;
import com.yoda.yodao.example.dao.PhotoDao;

/**
 * 
 * @author wellor
 * @version
 * @date 2015-3-25
 * 
 */
public class MySQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static final String DB_NAME = "ibeauty.db";

    private static final int DB_VERSION = 13;

    private static MySQLiteOpenHelper sInstance;

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DaoFactory.create(HairDao.class, null).onCreateTable(db);
        DaoFactory.create(PhotoDao.class, null).onCreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DaoFactory.create(HairDao.class, null).onUpgradeTable(db, oldVersion, newVersion);
        DaoFactory.create(PhotoDao.class, null).onUpgradeTable(db, oldVersion, newVersion);
        onCreate(db);
    }

    public synchronized static MySQLiteOpenHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MySQLiteOpenHelper(context);
        }
        return sInstance;
    }

    public void init() {
        getWritableDatabase();
    }

    public static boolean deleteDataBase(Context context) {
        sInstance.close();
        boolean isSuccess = context.deleteDatabase(DB_NAME);
        if (isSuccess) {
            getInstance(context);
        }
        return isSuccess;
    }
}

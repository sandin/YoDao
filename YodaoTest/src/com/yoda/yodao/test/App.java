package com.yoda.yodao.test;

import android.database.sqlite.SQLiteOpenHelper;

import com.hispeed.magician.dao.HairDao;
import com.yoda.yodao.DaoFactory;

public class App {

	public static void main(String[] args) {
		SQLiteOpenHelper db = null; // TODO
		
		DaoFactory.create(HairDao.class, db);
	}

}

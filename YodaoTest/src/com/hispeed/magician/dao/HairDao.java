package com.hispeed.magician.dao;

import android.database.sqlite.SQLiteOpenHelper;

import com.hispeed.magician.model.Hair;
import com.yoda.yodao.BaseDao;
import com.yoda.yodao.annotation.Repository;

@Repository
public abstract class HairDao extends BaseDao<Hair> {

	public HairDao(SQLiteOpenHelper openHelper) {
		super(openHelper);
	}

}

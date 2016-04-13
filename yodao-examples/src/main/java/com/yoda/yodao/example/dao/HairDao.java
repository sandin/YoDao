package com.yoda.yodao.example.dao;

import android.database.sqlite.SQLiteOpenHelper;

import com.yoda.yodao.example.model.Hair;
import com.yoda.yodao.BaseDao;
import com.yoda.yodao.annotation.Repository;

@Repository
public abstract class HairDao extends BaseDao<Hair, Long> {

	public HairDao(SQLiteOpenHelper openHelper) {
		super(openHelper);
	}

}

package com.yoda.yodao.test.dao;

import com.yoda.yodao.YoDao;
import com.yoda.yodao.annotation.Repository;
import com.yoda.yodao.test.model.User;

@Repository
public interface UserDao extends YoDao<User> {
	
	/*
	List<User> findByNameAndAge(String name, int age);

	User findOneByNameAndAge(String name, int age);

	User findOneByNameOrderByAge(String name);
	*/

}

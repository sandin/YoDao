package com.yoda.yodao.test.dao;

import java.util.List;

import com.yoda.yodao.YoDao;
import com.yoda.yodao.annotation.Repository;
import com.yoda.yodao.test.model.User;

@Repository
public interface UserDao extends YoDao<User> {
	
	List<User> findByNameAndAgeOrderByAge(String name, int age);

	User findOneByNameGroupByNameOrderByAge(String name);
	
	int deleteByName(String name);

}

package com.yoda.yodao.test;

import com.yoda.yodao.test.dao.UserDao;
import com.yoda.yodao.test.dao.impl.UserDaoImpl;
import com.yoda.yodao.test.model.User;


public class App {
	
	public static void main(String[] args) {
		UserDao userDao = new UserDaoImpl(null);
		User user = userDao.findOne(1);
		user.getAge();
	}

}

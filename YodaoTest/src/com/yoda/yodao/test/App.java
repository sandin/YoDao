package com.yoda.yodao.test;

import com.yoda.yodao.test.dao.UserDao;
import com.yoda.yodao.test.dao.impl.UserDaoImpl;

public class App {
	
	public static void main(String[] args) {
		
		UserDao dao = new UserDaoImpl();
		dao.findAll();
		
	}

}

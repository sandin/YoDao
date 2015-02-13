package com.yoda.yodao.test;

import java.util.List;

import com.hispeed.magician.dao.CustomerDao;
import com.hispeed.magician.model.Customer;

public class App {
	
	public static void main(String[] args) {
		CustomerDao dao = new CustomerDao(null);
		List<Customer> list = dao.findAll();
	}

}

package com.yoda.yodao.example.dao;

import com.yoda.yodao.YoDao;
import com.yoda.yodao.annotation.Repository;
import com.yoda.yodao.example.model.User;

import java.util.List;

/**
 * Created by lds on 2016/4/14.
 */
@Repository
public interface UserDao extends YoDao<User, Long> {

    User findOneByName(String name);

    List<User> findListByNameAndAge(String name, int age);

    List<User> findListOrderByAge();

    List<User> findListOrderByAgeDesc();

    long countByAge(int age);

    int updateByName(User user, String name);

    User findOneByUserName(String username);


}

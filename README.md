YoDao
============

YoDao Is A Simple and Fast ORM library for android.


Usage
-----

Step 1: Write your model, use JAP(Java Persistence API) style.

```java
    package com.yoda.yodao.test.model;

    import com.yoda.yodao.annotation.Column;
    import com.yoda.yodao.annotation.Entity;
    import com.yoda.yodao.annotation.GeneratedValue;
    import com.yoda.yodao.annotation.GenerationType;
    import com.yoda.yodao.annotation.Id;
    import com.yoda.yodao.annotation.Table;

    @Entity
    @Table(name="user")
    public class User {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private long id;

      @Column(name = "name")
      private String name;

      @Column(name = "age")
      private long age;

      public long getId() {
        return id;
      }

      public void setId(long id) {
        this.id = id;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public long getAge() {
        return age;
      }

      public void setAge(long age) {
        this.age = age;
      }
    }
```


Step 2: Write your DAO interface. (Yes, just a interface)


```java
    package com.yoda.yodao.test.dao;

    import java.util.List;

    import com.yoda.yodao.YoDao;
    import com.yoda.yodao.annotation.Repository;
    import com.yoda.yodao.test.model.User;

    @Repository
    public interface UserDao extends YoDao<User> {

      List<User> findByNameAndAge(String name, int age);

      User findOneByNameAndAge(String name, int age);

      User findOneByNameOrderByAge(String name);

    }
```


Step 3: Just use this Dao in your code, Yodao will generate all the codes for your, include create table, Dao implements Class.

```java
    UserDao userDao = new UserDaoImpl(dbOpenHelper);

    // Query
    List<User> users = userDao.findAll();
    users = userDao.findByNameAndAge("Jackson", 23);
    user = userDao.findOneByName("Jackson")

    // Insert
    User uesr = new User();
    user.setName("Jackson");
    user.setAge(23);
    userDao.save(user);

    // Update
    user.setName("Jack");
    userDao.save(user);

    // Delete
    userDao.delete(user.getId());
    userDao.deleteAll();
```


Performance
-----------

Yodao is very fast, because it uses annotation processing to generate all codes for you, just like [Butterknife](https://github.com/JakeWharton/butterknife), and [Dagger](https://github.com/square/dagger) .



License
-------

    Copyright 2015 Dingsan Lau

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

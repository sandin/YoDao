# YoDao框架说明

[TOC]

## 简要说明

YoDao是一个简单和快速的ORM框架。

### 技术实现：

项目受J2EE的Hibernate等ORM框架启发，并实现了Java Persistence API标准，在大部分系统设计学习了Sprint Data JPA框架，在使用和API上都和其相似。

在android上为了提高效率，没有使用运行时的反射来解析注解，而使用dagger和butterknife一样的技术在编译时解析注解并生成代码，因此非常高效。

### 框架结构：

整个框架和butterknife一样，分为三个子项目：

1. 主项目，这是一个Android library项目，在使用的时候引用即可。
2. 编译项目，这是一个编译工具，在IDE或gradle里配置即可，无需打包到APK里。
3. 实例项目，DEMO演示。



## 使用说明

基本使用是和dagger一样，这里仅描述android studio的使用方式：

### 1. 修改gradle配置

在 `build.gradle` 文件加入一下配置：

```groovy
apply plugin: 'com.neenbedankt.android-apt'

// ...

buildscript {
	dependencies {
    	classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
	}
}

repositories {
    jcenter()
    maven { url 'https://dl.bintray.com/sandin/maven/' }
}

dependencies {
	compile 'com.lds:yodao:0.2.1'
  	apt 'com.lds:yodao-compiler:0.2.1'
}
```

NOTE: 目前使用bintray的maven源，以后将上传jcenter

### 2. JavaBean增加注解

```Java
import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;

@Entity
@Table(name="user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column(name = "age")
    private int age;

    public User() {
    }

  	// Getter & Setter
    // ...
}
```

这里使用的注解和JPA一模一样，只是应该android里面没有 `javax.persistence `包，所以所有的注解都放置在 `com.yoda.yodao.annotation` 包下，但注解类是一样的，因此和是用了JPA标准的实体类是完全兼容的。

这里注意Getter&Setter方法的命令必须完全遵循POJO的命名规范，否则会编译不过。

### 3. DAO的定义

这里设计和Spring Data JPA框架一样，只要定义个 `interface` ，并继承 `YoDao` 即完成了Dao的工作，框架会根据这个接口的定义自动生成实现类的代码。

例如：

```Java
public interface UserDao extends YoDao<User, Long> {

    User findOneByName(String name);

    List<User> findListByNameAndAge(String name, int age);

    List<User> findListOrderByAge();

    List<User> findListOrderByAgeDesc();
    
    long countByAge(int age);

}
```

YoDao的两个泛型：

1. T，映射的实体类
2. ID，主键PK的类



### 4. 在数据库里建表

框架已经将所有实体对应的table的建表SQL都生成了，只需要在建表的时候调用：

```java
public class MySQLiteOpenHelper 
			extends android.database.sqlite.SQLiteOpenHelper {
  
    @Override
    public void onCreate(SQLiteDatabase db) {
        DaoFactory.create(HairDao.class, null).onCreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DaoFactory.create(HairDao.class, null).onUpgradeTable(db, oldVersion, newVersion);
        onCreate(db);
    }
    
}
```

在修改实体类以后（如增加字段），只需要重新build一下，重建表的时候表的数据结构就会变化。

 

### 5. 使用你的Dao

```Java
UserDao userDao = DaoFactory.create(UserDao.class, mSQLiteOpenHelper);

// insert
User user = new User();
user.setName("Jack");
user.setAge(23);
userDao.save(user);

// update
user.setName("New Name");
userDao.update(user);

// query
List<User> users = userDao.findAll();
user = userDao.findOne(user.getId());

// delete
userDao.delete(user); 
```



## 注解说明

### @Entity

使用在需要映射的实体类上，框架只会处理有此注解的实体类。

### @Table(name='table_ame')

使用在需要映射的实体类上，属性name为表名，不提供name的时候将自动使用类名作为表名。（表名的命名规范是全部小写，下划线分割：如class名为 `UserInfo` 表名自动是 `user_info`) 

### @Id

使用在需要映射成表主键的成员变量上，主键支持 `int` `long` `String` 类型。

### @GeneratedValue

使用在需要映射成表主键的成员变量上，

属性`strategy`为主键的生成策略：

1. `AUTO` 主键自增
2. `UUID` 主键使用UUID随机生成

### @Column(name="column_ame")

使用在需要映射的成员变量上

属性`name`为表列名，不提供name的时候将默认使用变量名作为列名（一样会转成全部小写，下划线分割的命名规范）

属性`unique`对于SQL里面的UNIQUE

属性`nullable`对应SQL里面的NOT NULL

属性`length`对于SQL里面的长度限制

其他属性请参考JPA。

### @NotColumn

使用在不需要映射的成员变量上，因为框架会将需要实体类的所有成员变量都进行映射，使用了该注解告诉框架这个字段不需要在表中持久化，则在建表时忽略该字段。

### @Repository

使用在自己编写的DAO上面，表示该DAO是需要框架来生成实现类的代码。

### @Query

使用在DAO的方法上面，表示这个方法不需要自动生成，自定义SQL查询。

属性`value`为自定义查询的SQL语句。

目前仅支持原生SQL，今后将支持在HSQL语法。

### 其他注解

今后将陆续开始支持 `OneToOne` `OneToMany` `ManyToOne` `ManyToMany` 等JPA的注解。

## YoDao增删改查接口

### save()

```java
/**
 * Saves a given entity. Use the returned instance for further operations as
 * the save operation might have changed the entity instance completely.
 *
 * @param entity
 * @return the saved entity
 */
boolean save(T entity);

/**
 * Saves all given entities.
 *
 * @param entities
 * @return
 */
boolean save(List<T> entities);
```

### update()

```java
/**
    * update a row by primary key
    * 
    * @param entity
    * @return
    */
int update(T entity);

/**
 * update by fields
 * 
 * @param entity
 * @param whereClause
 * @param whereArgs
 * @return
 */
int updateByFields(T entity, String whereClause,
           String[] whereArgs);

/**
 * update some fields
 * 
 * @param values
 * @param whereClause
 * @param whereArgs
 * @return
 */
int updateByFields(ContentValues values, String whereClause,
           String[] whereArgs);
```

### find()

```java
/**
 * Retrives an entity by its primary key.
 *
 * @param id
 * @return the entity with the given primary key or {@code null} if none
 *         found
 * @throws IllegalArgumentException
 *             if primaryKey is {@code null}
 */
T findOne(ID id);

/**
 * Retrives an entity by its fields
 * 
 * @param selection
 * @param selectionArgs
 * @param groupBy
 * @param having
 * @param orderBy
 * @return
 */
T findOneByFields(String selection, String[] selectionArgs, String groupBy,
      String having, String orderBy);

/**
 * Retrives an entity by its fields
 * 
 * @param selection
 * @param selectionArgs
 * @param orderBy
 * @return
 */
T findOneByFields(String selection, String[] selectionArgs, String orderBy);

/**
 * Retrives an entity by sql
 * 
 * @param sql
 * @return
 */
T findOneBySql(String sql, String[] selectionArgs);


```

```java
/**
 * Returns all instances of the type.
 *
 * @return all entities
 */
List<T> findAll();

/**
 * Find List By Fields
 * 
 * @param selection
 * @param selectionArgs
 * @param groupBy
 * @param having
 * @param orderBy
 * @return
 */
List<T> findListByFields(String selection, String[] selectionArgs,
      String groupBy, String having, String orderBy);

/**
 * Find List By Fields
 * 
 * @param selection
 * @param selectionArgs
 * @param orderBy
 * @return
 */
List<T> findListByFields(String selection, String[] selectionArgs,
      String orderBy);

/**
 * Find List By SQL
 * 
 * @param sql
 * @return
 */
List<T> findListBySql(String sql, String[] selectionArgs);
```



### delete()

```java
/**
 * Deletes the entity with the given id.
 * 
 * @param id
 */
int delete(ID id);

/**
 * Deletes a given entity.
 *
 * @param entity
 */
int delete(T entity);

/**
 * Deletes the given entities.
 *
 * @param entities
 */
int delete(List<T> entities);

/**
 * Deletes all entities managed by the repository.
 */
int deleteAll();

/**
 * Deletes entities by fields
 * 
 * @param selection
 * @param selectionArgs
 * @return
 */
int deleteByFields(String selection, String[] selectionArgs);
```



### exists()

```java
/**
 * Returns whether an entity with the given id exists.
 *
 * @param id
 * @return true if an entity with the given id exists, alse otherwise
 * @throws IllegalArgumentException
 *             if primaryKey is {@code null}
 */
boolean exists(ID id);
```



### count()

```java
/**
 * Returns the number of entities available.
 *
 * @return the number of entities
 */
long count();

/**
 * Returns the number of entities with selections available.
 * 
 * @param selections
 * @param selectionArgs
 * @return
 */
long countByFields(String selections, String[] selectionArgs);
```



### onCreateTable()

这个方法在SQLOpenHelper中的onCreate()中调用，调用后才会执行建表语句。

### onUpgradeTable()

这个方法在SQLOpenHelper中的onUpgrade()中调用，调用后才会执行删除表语句。



## DaoFactory工厂类

### 自动生成的DAO

实现类

默认情况下，框架会根据你写的dao的包名和类名来生成实现类的包名和类名。

例如，你的Dao为：

```java
com.your.test.dao.UserDao
```

那么生成的实现类名为：

```java
com.your.test.dao.impl.UserDaoImpl
```

在android studio里，自动的生成的代码在项目根目录　`build/source/apt/debug` ，这个目录由插件决定，eclipse上可以配置。

### 实例化DAO

框架推荐不直接调用和实例化自动生成的Impl类，而应该使用 `DaoFactory` 来实例化Dao，例如：

```java
UserDao userDao = DaoFactory.create(UserDao.class, mSQLiteOpenHelper);
```

今后将支持通过注解来定义生命周期以支持对于指定Dao可选性的单例模式。目前所有Dao都默认非单例。



## DAO的写法

### 自定义Dao

一般情况下，自定义的 `dao` 只需要写一个空的　`interface` 然后继承 `YoDao`接口即够用了，因为接口里实现了大量增删改查的API。

例如：

```java
public interface UserDao extends YoDao<User, Long> {

}
```

注意：两个泛型不要搞反了，第一个是实体类，第二个是主键。今后框架将会校验，目前还没有。

### 方法的定义

对于接口无法满足的时候，则可以通过定义接口的方法来实现，这里必须按照指定的约定来写方法名，框架则会按照方法的定义来帮你实现。

以下列出所有支持的方法：

#### WHERE

```java
User findOneByName(String name);
// SQL: select * from user where name = ? limit 1

// AND
List<User> findListByNameAndAge(String name, int age);
// SQL: select * from user where name = ? and age = ?

// OR
List<User> findListByNameOrAge(String name, int age);
// SQL: select * from user where name ? or age = ?
```

#### GROUP BY
```java
List<User> findListByNameGroupByAge(String name);
// SQL: select * from user where name = ? group by age;

// HAVINGS
// 暂不支持
```

#### ORDER BY
```java
List<User> findListOrderByAge();
// SQL: select * from user order by age

List<User> findListOrderByAgeDesc();
// SQL: select * from user order by age DESC
```

#### UPDATE
```java
int updateByName(User user, String name);
// SQL: update user ... where name = ?
```

####  DELETE
```java
int deleteByName(String name);
// SQL: delete from user where name = ?
```

####  COUNT
```java
long countByAge(int age);
```

#### 原生SQL
```java
@Query(value= "select * from user where name = ?")
User findByUsername(String username);
```



#### 关键要点：

1. 所有SQL的关键字都有意义，不能随便将其放置在方法名中，如 `BY` `Order` `Where` 等。
2. 注意字段名的命名规范，这里会将字段名转成和字段名一样的命名规范，即全部小写，下划线分词，例如　`findByUserName` 那么则直接使用 `where user_name = ?`的SQL来查找，并且会使用 `setUserName()` 和　`getUserName()` 方法来设值和取值。Java的字段名和方法名都保持驼峰命名法，数据库的表名和列名都按全部小写，下划线分割的命名法。

#### 方法的参数

方法的参数的个数和顺序必须和方法定义的查询保持一致。

#### 方法的返回值

方法的返回值类型只能是：　

1. `T`  find语句
2. `List<T>`　find语句
3. `boolean`　exists语句
4. `long`　count语句
5. `int`　delete语句








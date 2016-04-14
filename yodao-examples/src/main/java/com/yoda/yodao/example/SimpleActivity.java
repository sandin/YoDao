package com.yoda.yodao.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.yoda.yodao.DaoFactory;
import com.yoda.yodao.example.dao.PhotoDao;
import com.yoda.yodao.example.dao.UserDao;
import com.yoda.yodao.example.model.Photo;
import com.yoda.yodao.example.model.User;

import java.util.List;


public class SimpleActivity extends Activity {

    private PhotoDao mPhotoDao;
    private MySQLiteOpenHelper mSQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_activity);

        testDao();
    }

    private void testDao() {
        mSQLiteOpenHelper = MySQLiteOpenHelper.getInstance(this);

        mPhotoDao = DaoFactory.create(PhotoDao.class, mSQLiteOpenHelper);
        long count = mPhotoDao.count();
        System.out.println("count: " + count);

        Photo photo = new Photo();
        photo.setUrl("http://www.baidu.com");
        mPhotoDao.save(photo);

        List<Photo> list = mPhotoDao.findAll();
        System.out.println("list: " + list);
        Toast.makeText(getApplicationContext(), list.toString(), Toast.LENGTH_LONG).show();

        ((TextView) findViewById(R.id.text1)).setText(list.toString());

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

    }
}

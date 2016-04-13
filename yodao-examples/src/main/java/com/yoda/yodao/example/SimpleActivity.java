package com.yoda.yodao.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yoda.yodao.DaoFactory;
import com.yoda.yodao.example.dao.PhotoDao;
import com.yoda.yodao.example.model.Photo;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class SimpleActivity extends Activity {

    private PhotoDao mPhotoDao;
    private MySQLiteOpenHelper mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_activity);

        testDao();
    }

    private void testDao() {
        mDb = MySQLiteOpenHelper.getInstance(this);

        mPhotoDao = DaoFactory.create(PhotoDao.class, mDb);
        long count = mPhotoDao.count();
        System.out.println("count: " + count);

        Photo photo = new Photo();
        photo.setUrl("http://www.baidu.com");
        mPhotoDao.save(photo);

        List<Photo> list = mPhotoDao.findAll();
        System.out.println("list: " + list);
        Toast.makeText(getApplicationContext(), list.toString(), Toast.LENGTH_LONG).show();

        ((TextView) findViewById(R.id.text1)).setText(list.toString());
    }
}

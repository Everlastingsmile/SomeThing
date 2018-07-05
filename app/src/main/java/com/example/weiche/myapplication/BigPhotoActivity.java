package com.example.weiche.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.weiche.myapplication.base.BaseActivity;
import com.example.weiche.myapplication.bean.PhotoGirl;
import com.example.weiche.myapplication.widget.photoview.PhotoView;
import com.example.weiche.myapplication.util.GlideHelper;

public class BigPhotoActivity extends BaseActivity {
    public static final String INTENT_EXTRA_PHOTOGIRL = "PhotoGirl";

    private PhotoView mPhotoView;
    private PhotoGirl mPhotoGirl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);
        mPhotoView = (PhotoView) findViewById(R.id.photo_view);
        initData();
    }

    private void initData() {
        mPhotoGirl = (PhotoGirl) getIntent().getSerializableExtra(INTENT_EXTRA_PHOTOGIRL);
        if (mPhotoGirl == null){
            Toast.makeText(this,"错误：参数为null",Toast.LENGTH_SHORT).show();
            return;
        }
        GlideHelper.get(this,mPhotoGirl.getUrl()).into(mPhotoView);
    }
}

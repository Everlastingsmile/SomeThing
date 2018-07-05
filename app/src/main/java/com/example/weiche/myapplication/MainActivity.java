package com.example.weiche.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.weiche.myapplication.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this,PictureListActivity.class));
        finish();
    }
}

package com.example.weiche.myapplication.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int getLayoutResource(){
        return 0;
    }
}

package com.example.test2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/test2/TestJumpActivity")
public class TestJumpActivity extends BaseActivity {

    private TextView jump_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jump);
        ARouter.getInstance().inject(this);
        jump_button = (TextView) findViewById(R.id.jump_button);
        jump_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_button:
                ARouter.getInstance().build("/app/MainActivity").navigation();
                break;
        }
    }
}

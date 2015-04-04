package com.yastart.papaya.activities;

import android.os.Bundle;

import com.yastart.papaya.R;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.app_name);
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return false;
    }

    //test comment
}

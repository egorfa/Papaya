package com.yastart.papaya.activities;

import android.os.Bundle;

import com.yastart.papaya.R;

/**
 * Created by 123 on 05.04.2015.
 */
public class BookActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_book;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.book_activity);
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return false;
    }
}

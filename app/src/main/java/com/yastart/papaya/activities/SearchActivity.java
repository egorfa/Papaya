package com.yastart.papaya.activities;

import android.os.Bundle;
import android.widget.EditText;

import com.yastart.papaya.R;

/**
 * Created by 123 on 04.04.2015.
 */
public class SearchActivity extends BaseActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editText = (EditText)findViewById(R.id.editSearch);
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.search_book);
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return true;
    }
}

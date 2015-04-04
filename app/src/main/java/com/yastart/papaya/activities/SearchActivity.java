package com.yastart.papaya.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.yastart.papaya.R;

public class SearchActivity extends BaseActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editText = (EditText) findViewById(R.id.editSearch);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.last);
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return true;
    }
}

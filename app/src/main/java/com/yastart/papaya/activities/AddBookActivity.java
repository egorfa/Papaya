package com.yastart.papaya.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.yastart.papaya.R;

public class AddBookActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_add_book;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.add_book);
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return true;
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
}

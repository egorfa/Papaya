package com.yastart.papaya.activities;

import android.os.Bundle;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.R;

import java.util.ArrayList;

/**
 * Created by Egor on 04.04.2015.
 */
public class ProfileActivity extends BaseActivity {


    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_profile;
    }

    @Override
    protected String getTitleToolBar() {
        return "Профиль";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ArrayList<Book> Books = new ArrayList<>();
        Book book = new Book();
    }

}

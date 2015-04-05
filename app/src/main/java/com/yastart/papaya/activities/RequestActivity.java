package com.yastart.papaya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;

import java.util.ArrayList;

/**
 * Created by Egor on 05.04.2015.
 */
public class RequestActivity extends BaseActivity {

    private RecyclerView grid;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent =  getIntent();
        //Request request = intent.getParcelableExtra("request");

        grid = (RecyclerView) findViewById(R.id.my_books_grid);
        grid.setHasFixedSize(true);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setLayoutManager(new GridLayoutManager(mContext, 2));


    }

    private void loadBooks() {
        User user = new User();


        Book.getBooksForUser(user, new GetListHandler<Book>() {
            @Override
            public void done(ArrayList<Book> data) {
                books = data;
                //grid.setAdapter(new MyBooksGridAdapter(mContext, books, MyBooksFragment.this));
            }

            @Override
            public void error(String responseError) {
                Log.d("ERROR", responseError);
            }
        });
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_request;
    }

    @Override
    protected String getTitleToolBar() {
        return null;
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

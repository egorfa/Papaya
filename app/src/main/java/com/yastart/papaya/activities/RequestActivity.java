package com.yastart.papaya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.Request;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.MyBooksGridAdapter;

import java.util.ArrayList;

/**
 * Created by Egor on 05.04.2015.
 */
public class RequestActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView grid;
    private ArrayList<Book> books;

    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent =  getIntent();
        request = intent.getParcelableExtra("request");

        grid = (RecyclerView) findViewById(R.id.my_books_grid);
        grid.setHasFixedSize(true);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setLayoutManager(new GridLayoutManager(mContext, 2));

        loadBooks();

    }

    private void loadBooks() {

        User u = new User();
        u.setId(request.getId());
        Book.getBooksForUser(u, new GetListHandler<Book>() {
            @Override
            public void done(ArrayList<Book> data) {
                books = data;
                grid.setAdapter(new MyBooksGridAdapter(mContext, books, RequestActivity.this));
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

    @Override
    public void onClick(View view) {

    }
}

package com.yastart.papaya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetItemHandler;
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
    private ImageView imgDesired;
    private ImageView imgInReturn;

    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent =  getIntent();
        request = intent.getParcelableExtra("request");

        grid = (RecyclerView) findViewById(R.id.user_books);
        grid.setHasFixedSize(true);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setLayoutManager(new GridLayoutManager(mContext, 2));

        imgDesired = (ImageView) findViewById(R.id.img_desired);
        imgInReturn = (ImageView) findViewById(R.id.img_in_return);

        //Desired


        loadBooks();
        loadImage();

    }

    private void loadBooks() {

        User u = new User();
        //u.setId(request.getId());
        u.setId("1636319331666438");
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

    private void loadImage()
    {
        String i = request.getBookDesiredID();
        Book.getBookByID(request.getBookDesiredID(), new GetItemHandler<Book>(){
            @Override
            public void done(Book data) {
                Glide.with(mContext)
                        .load(data.getCoverUrl())
                        .into(imgDesired);
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
        return true;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.book_cell:
                final int position = grid.getChildLayoutPosition(view);
                ImageView imgView = (ImageView) view.findViewById(R.id.book_image);
                //((BitmapDrawable)imgView.getDrawable()).getBitmap();
                imgInReturn.setImageDrawable(imgView.getDrawable());
                break;
        }
    }
}

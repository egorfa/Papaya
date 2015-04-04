package com.yastart.papaya.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetHandler;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.MyBooksGridAdapter;

import java.util.ArrayList;

public class MyBooksFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView grid;
    private ArrayList<Book> books;

    public static MyBooksFragment newInstance() {
        MyBooksFragment pageFragment = new MyBooksFragment();
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_books, container, false);
        mContext = rootView.getContext();

        grid = (RecyclerView) rootView.findViewById(R.id.my_books_grid);
        grid.setHasFixedSize(true);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setLayoutManager(new GridLayoutManager(mContext, 2));

        final FloatingActionButton addBookButton = (FloatingActionButton) rootView.findViewById(R.id.add_book_button);
        addBookButton.setOnClickListener(this);
        addBookButton.attachToRecyclerView(grid);

        User user = new User();
        user.setId("102363055574899025750");
        Book.getBooksForUser(user, new GetHandler<Book>() {
            @Override
            public void done(ArrayList<Book> data) {
                Log.d("TAG", "" + data.size());
                books = data;
                grid.setAdapter(new MyBooksGridAdapter(mContext, books, MyBooksFragment.this));
            }

            @Override
            public void error(String responseError) {
                Log.d("ERROR", responseError);
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_cell:
                final int position = grid.getChildLayoutPosition(v);
                Toast.makeText(mContext, "Pressed " + position, Toast.LENGTH_SHORT).show();
                // TODO startBookActivity
                break;
            case R.id.add_book_button:
                Toast.makeText(mContext, "Pressed", Toast.LENGTH_SHORT).show();
                // TODO startAddBookActivity
                break;
        }
    }
}

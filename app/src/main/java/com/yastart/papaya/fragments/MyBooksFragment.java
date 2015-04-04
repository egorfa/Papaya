package com.yastart.papaya.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.MyBooksGridAdapter;

import java.util.ArrayList;

public class MyBooksFragment extends BaseFragment implements View.OnClickListener {

    public MyBooksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_my_books, container, false);
        mContext = rootView.getContext();

        final RecyclerView grid = (RecyclerView) rootView.findViewById(R.id.my_books_grid);
        grid.setHasFixedSize(true);
        grid.setItemAnimator(new DefaultItemAnimator());
        grid.setLayoutManager(new GridLayoutManager(mContext, 2));

        ArrayList<Book> books = null; // TODO get books

        grid.setAdapter(new MyBooksGridAdapter(mContext, books, this));

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}

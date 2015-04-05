package com.yastart.papaya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;
import com.yastart.papaya.activities.AddBookActivity;
import com.yastart.papaya.activities.BookActivity;
import com.yastart.papaya.adapters.MyBooksGridAdapter;

import java.util.ArrayList;

public class MyBooksFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView grid;
    private ArrayList<Book> books;
    private SwipeRefreshLayout refreshLayout;

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

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    private void loadBooks() {
//        User user = User.getCurrentUser();

        User user = User.getCurrentUser();
        Book.getBooksForUser(user, new GetListHandler<Book>() {
            @Override
            public void done(ArrayList<Book> data) {
                refreshLayout.setRefreshing(false);
                books = data;
                grid.setAdapter(new MyBooksGridAdapter(mContext, books, MyBooksFragment.this));
            }

            @Override
            public void error(String responseError) {
                refreshLayout.setRefreshing(false);
                Log.d("ERROR", responseError);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_cell:
                final int position = grid.getChildLayoutPosition(v);
//                Toast.makeText(mContext, "Pressed " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getBaseContext(), BookActivity.class);
                intent.putExtra(BookActivity.EXTRA_BOOK, books.get(position));
                intent.putExtra(BookActivity.EXTRA_IS_CURRENT_USER_BOOK, true);
                startActivity(intent);
                break;
            case R.id.add_book_button:
                mContext.startActivity(new Intent(mContext, AddBookActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadBooks();
    }
}

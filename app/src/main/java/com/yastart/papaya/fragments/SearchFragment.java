package com.yastart.papaya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;
import com.yastart.papaya.activities.BookActivity;
import com.yastart.papaya.activities.SearchActivity;
import com.yastart.papaya.adapters.DividerItemDecoration;
import com.yastart.papaya.adapters.ProfileBooksListAdapter;

import java.util.ArrayList;

public class SearchFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView list;
    private ArrayList<Book> books;
    private SwipeRefreshLayout refreshLayout;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mContext = view.getContext();
        View search = view.findViewById(R.id.find_books_layout);

        list = (RecyclerView) view.findViewById(R.id.search_books_list);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(mContext));
        list.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.primary, R.color.primary_dark);

        loadBooks();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    private void loadBooks() {
        User user = new User();
//        user.setId("102363055574899025750");
        user.setId("117211419728589565827");
        Book.getBooksForCity("Moscow", new GetListHandler<Book>() {
            @Override
            public void done(ArrayList<Book> data) {
                refreshLayout.setRefreshing(false);
                books = data;
                list.setAdapter(new ProfileBooksListAdapter(mContext, books, SearchFragment.this));
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
            case R.id.book_search:
                final int position = list.getChildLayoutPosition(v);
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.putExtra(BookActivity.EXTRA_BOOK, books.get(position));
                intent.putExtra(BookActivity.EXTRA_IS_CURRENT_USER_BOOK, false);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        loadBooks();
    }
}

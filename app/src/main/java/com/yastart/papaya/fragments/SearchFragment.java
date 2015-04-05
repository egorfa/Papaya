package com.yastart.papaya.fragments;

import android.content.Intent;
import android.os.Bundle;
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

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    View search;
    RecyclerView list;
    ArrayList<Book> books;

    public static SearchFragment newInstance() {
        SearchFragment pageFragment = new SearchFragment();
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);

        search = view.findViewById(R.id.find_books_layout);


        list = (RecyclerView) view.findViewById(R.id.listView1);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        list.addItemDecoration(new DividerItemDecoration(getActivity().getBaseContext(), DividerItemDecoration.VERTICAL_LIST));

        loadBooks();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadBooks() {
//        User user = User.getCurrentUser();

        User user = new User();
//        user.setId("102363055574899025750");
        user.setId("117211419728589565827");
        Book.getBooksForCity("Moscow", new GetListHandler<Book>() {
            @Override
            public void done(ArrayList<Book> data) {
                books = data;
                list.setAdapter(new
                        ProfileBooksListAdapter(getActivity().getBaseContext(), books, SearchFragment.this));
            }

            @Override
            public void error(String responseError) {
                Log.d("ERROR", responseError);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_search:
                final int position = list.getChildLayoutPosition(v);
                Intent intent = new Intent(getActivity().getBaseContext(), BookActivity.class);
                intent.putExtra("book", books.get(position));
                startActivity(intent);
                // TODO startBookActivity
                break;
        }
    }
}

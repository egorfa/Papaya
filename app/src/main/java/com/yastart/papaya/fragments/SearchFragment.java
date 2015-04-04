package com.yastart.papaya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.R;
import com.yastart.papaya.activities.BookActivity;
import com.yastart.papaya.activities.SearchActivity;
import com.yastart.papaya.adapters.DividerItemDecoration;
import com.yastart.papaya.adapters.ProfileBooksListAdapter;

import java.util.ArrayList;

/**
 * Created by 123 on 04.04.2015.
 */
public class SearchFragment extends BaseFragment implements View.OnClickListener {

    EditText search;

    public static SearchFragment newInstance() {
        SearchFragment pageFragment = new SearchFragment();
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);

        search = (EditText) view.findViewById(R.id.textSearch);

        ArrayList<Book> Books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setTitle("Заголовок" + String.valueOf(i));
            book.setAuthors("Автор");
            Books.add(book);
        }

        final RecyclerView list = (RecyclerView) view.findViewById(R.id.listView1);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        list.addItemDecoration(new DividerItemDecoration(getActivity().getBaseContext(), DividerItemDecoration.VERTICAL_LIST));

        list.setAdapter(new ProfileBooksListAdapter(getActivity().getBaseContext(), Books, this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getBaseContext(), BookActivity.class);
        startActivity(intent);
    }
}

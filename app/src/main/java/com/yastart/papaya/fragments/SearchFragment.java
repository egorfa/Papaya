package com.yastart.papaya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.widget.EditText;
import android.widget.Toast;
>>>>>>> 250d97393e05d3c616c0007b2c2ea79ed19208f6

import com.yastart.papaya.Model.Book;
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

        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setTitle("Заголовок" + String.valueOf(i));
            book.setAuthors("Автор");
            books.add(book);
        }
        books = Books;

        list = (RecyclerView) view.findViewById(R.id.listView1);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        list.addItemDecoration(new DividerItemDecoration(getActivity().getBaseContext(), DividerItemDecoration.VERTICAL_LIST));

        list.setAdapter(new
                ProfileBooksListAdapter(getActivity().getBaseContext(), books, this));

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
        switch (v.getId()) {
            case R.id.book_search:
                final int position = list.getChildLayoutPosition(v);
                Toast.makeText(getActivity().getBaseContext(), "Pressed " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getBaseContext(), BookActivity.class);
                intent.putExtra("book", books.get(position));
                startActivity(intent);
                // TODO startBookActivity
                break;
        }
    }
}

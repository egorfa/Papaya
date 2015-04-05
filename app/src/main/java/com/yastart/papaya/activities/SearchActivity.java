package com.yastart.papaya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.DividerItemDecoration;
import com.yastart.papaya.adapters.ProfileBooksListAdapter;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    private RecyclerView booksList;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        books = new ArrayList<>();

        final EditText searchField = (EditText) findViewById(R.id.editSearch);
        searchField.addTextChangedListener(this);

        booksList = (RecyclerView) findViewById(R.id.search_books_list);
        booksList.setHasFixedSize(true);
        booksList.setItemAnimator(new DefaultItemAnimator());
        booksList.setLayoutManager(new LinearLayoutManager(mContext));
        booksList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.last);
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO
        booksList.setAdapter(new ProfileBooksListAdapter(mContext, books, this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_search:
                final int position = booksList.getChildLayoutPosition(v);
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.putExtra(BookActivity.EXTRA_BOOK, books.get(position));
                intent.putExtra(BookActivity.EXTRA_IS_CURRENT_USER_BOOK, false);
                startActivity(intent);
                break;
        }
    }
}

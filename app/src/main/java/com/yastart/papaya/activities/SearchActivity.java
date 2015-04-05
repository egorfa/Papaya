package com.yastart.papaya.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.DividerItemDecoration;
import com.yastart.papaya.adapters.MarketAdapter;
import com.yastart.papaya.adapters.ProfileBooksListAdapter;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements TextWatcher, View.OnClickListener {

    private RecyclerView booksList;
    private ArrayList<Book> books;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        books = new ArrayList<>();

        searchField = (EditText) findViewById(R.id.editSearch);
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

    public static class MarketBooks {
        public static String[] titles = {
                "Война и мир",
                "Философия Java",
                "Программирование для Android"
        };

        public static String[] authors = {
                "Л.Н. Толстой",
                "Б. Эккель",
                "А. Голощапов"
        };

        public static String[] prices = {
                "1180 р.",
                "630 р.",
                "565 р."
        };

        public static String[] URLs = {
                "http://market.yandex.ru/product/4799651/?hid&show-uid=399751814282240941",
                "http://market.yandex.ru/product/10727012/?hid&show-uid=309929414282241181",
                "http://market.yandex.ru/product/9733702/?hid&show-uid=108432214282241802"
        };
    }

    @Override
    public void afterTextChanged(Editable s) {
        final String searchString = s.toString().trim();
        int index = containedInMarketBooks(searchString);

        if (searchString.isEmpty()) {
            books.clear();
            booksList.setAdapter(new ProfileBooksListAdapter(mContext, books, SearchActivity.this));
        } else if (searchString.length() >= 3 && index != -1) {
            booksList.setAdapter(new MarketAdapter(index, this));
        } else {
            Book.findBookByStr(searchString, new GetListHandler<Book>() {
                        @Override
                        public void done(ArrayList<Book> data) {
                            books = data;
                            booksList.setAdapter(new ProfileBooksListAdapter(mContext, books, SearchActivity.this));
                        }

                        @Override
                        public void error(String responseError) {
                            Log.d("TAG", "Error: " + responseError);
                        }
                    }

            );
        }

    }

    private int containedInMarketBooks(String searchString) {
        for (int i = 0; i < MarketBooks.titles.length; i++) {
            if (MarketBooks.titles[i].toLowerCase().contains(searchString.toLowerCase())) {
                return i;
            }
        }

        for (int i = 0; i < MarketBooks.authors.length; i++) {
            if (MarketBooks.authors[i].toLowerCase().contains(searchString.toLowerCase())) {
                return i;
            }
        }

        return -1;
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
            case R.id.market_card:
                final int adPosition = booksList.getChildLayoutPosition(v);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(MarketBooks.URLs[adPosition]));
                startActivity(i);
                break;
        }
    }
}

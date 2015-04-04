package com.yastart.papaya.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yastart.papaya.Model.Book;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.DividerItemDecoration;
import com.yastart.papaya.adapters.ProfileBooksListAdapter;

import java.util.ArrayList;

/**
 * Created by Egor on 04.04.2015.
 */
public class ProfileActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_profile;
    }

    @Override
    protected String getTitleToolBar() {
        return "Профиль";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ArrayList<Book> Books = new ArrayList<>();
        for(int i=0;i<10;i++) {
            Book book = new Book();
            book.setTitle("Заголовок" + String.valueOf(i));
            book.setAuthors("Автор");
            Books.add(book);
        }

        final RecyclerView list = (RecyclerView) findViewById(R.id.listView);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        list.setAdapter(new ProfileBooksListAdapter(getBaseContext(), Books, this));
    }

    @Override
    public void onClick(View v) {

    }

}

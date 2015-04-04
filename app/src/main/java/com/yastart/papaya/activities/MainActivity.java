package com.yastart.papaya.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.Model.VoidHandler;
import com.yastart.papaya.R;
import com.yastart.papaya.fragments.MyBooksFragment;
import com.yastart.papaya.fragments.MyProfileFragment;
import com.yastart.papaya.fragments.RequestsFragment;
import com.yastart.papaya.fragments.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private static int NUM_PAGES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.pagerTitleStrip);
        tabs.setViewPager(pager);

        User u = User.getCurrentUser();

        Book.getBooksForUser(u, new GetListHandler<Book>() {
            @Override
            public void done(ArrayList data) {
                Log.d("DB TEST", "TOTAL BOOKS = " + data.size());
                for (int i = 0; i < data.size(); i++) {
                    Log.d("DB TEST", "--------------------  " + data.toString());
                }
            }

            @Override
            public void error(String responseError) {
                Log.d("DB TEST", responseError);
            }
        });

        Book newBook = new Book();
        newBook.setAuthors("Makar");
        newBook.setTitle("My super new book");
        newBook.setDescription("Rather elaborate description");
        newBook.setCity("Moscow");
        newBook.setCoverUrl("https://ru.wikipedia.org/wiki/Яндекс.Книга");
        newBook.setCondition(Book.EXCELLENT);
        newBook.setOwnerID(u.getId());

        newBook.saveBook(new VoidHandler() {
            @Override
            public void done() {
                Log.d("DB TEST", "BOOK HAS BEEN SAVED");
            }

            @Override
            public void error(String responseError) {
                Log.d("DB TEST", "BOOK HASN'T SAVED " + responseError);
            }
        });

        Book.getBooksForUser(u, new GetListHandler<Book>() {
            @Override
            public void done(ArrayList data) {
                Log.d("DB TEST", "TOTAL BOOKS = " + data.size());
                for (int i = 0; i < data.size(); i++) {
                    Log.d("DB TEST", "--------------------  " + data.toString());
                }
            }

            @Override
            public void error(String responseError) {
                Log.d("DB TEST", responseError);
            }
        });

    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.app_name);
    }

    @Override
    protected boolean getDisplayHomeAsUp() {
        return false;
    }

    @Override
    protected boolean getHomeButtonEnabled() {
        return false;
    }

    private class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {
        FragmentManager fm;

        public CustomFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return SearchFragment.newInstance();
                case 1:
                    return MyBooksFragment.newInstance();
                case 2:
                    return RequestsFragment.newInstance();
                case 3:
                    return MyProfileFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.last);
                case 1:
                    return getString(R.string.my_books);
                case 2:
                    return getString(R.string.requests);
                case 3:
                    return getString(R.string.profile);
                default:
                    return "";
            }
        }

    }
}

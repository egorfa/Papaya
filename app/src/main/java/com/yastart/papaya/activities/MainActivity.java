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
import com.yastart.papaya.Model.GetItemHandler;
import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.Request;
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

        Request.getRequestsForUser(User.getCurrentUser(), new GetListHandler<ArrayList<Request>>() {
            @Override
            public void done(ArrayList<ArrayList<Request>> data) {
                Log.d("TEST", data.toString());
                System.out.println("HELLO");
            }

            @Override
            public void error(String responseError) {
                System.out.println("NO!");
            }
        });

//        Book.getBookByID("5139717033033728", new GetItemHandler<Book>() {
//            @Override
//            public void done(Book book) {
//                Request newRequest = new Request();
//                newRequest.setInitiatorID(User.getCurrentUser().getId());
//                newRequest.setResponderID(book.getOwnerID());
//                newRequest.setBookDesiredID(book.getId());
//
//                newRequest.save(new VoidHandler() {
//                    @Override
//                    public void done() {
//                        Log.d("SAVED", "SAAAAVED!!!!");
//                    }
//
//                    @Override
//                    public void error(String responseError) {
//                        Log.d("ERROR", responseError);
//                    }
//                });
//            }
//
//            @Override
//            public void error(String responseError) {
//                Log.d("ERROR", responseError);
//            }
//        });

//        Request newRequest = new Request();
//        newRequest.setInitiatorID(User.getCurrentUser().getId());
//        newRequest.setResponderID("117211419728589565827");
//        newRequest.setBookDesiredID("5139717033033728");
//
//        newRequest.save(new VoidHandler() {
//            @Override
//            public void done() {
//                Log.d("SAVED", "SAAAAVED!!!!");
//            }
//
//            @Override
//            public void error(String responseError) {
//                Log.d("ERROR", responseError);
//            }
//        });

//        Book.findBookByStr("Jx", new GetListHandler<Book>() {
//            @Override
//            public void done(ArrayList<Book> data) {
//                Log.d("TEST", ""+data.size());
//            }
//
//            @Override
//            public void error(String responseError) {
//
//            }
//        });
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

package com.yastart.papaya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetItemHandler;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.MyBooksGridAdapter;
import com.yastart.papaya.fragments.MyBooksFragment;

import java.util.ArrayList;

/**
 * Created by 123 on 05.04.2015.
 */
public class BookActivity extends BaseActivity {

    private String user_id;
    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imgBook = (ImageView) findViewById(R.id.imgDescr);
        imgProfile = (ImageView) findViewById(R.id.imgUser);
        TextView tvBookName = (TextView) findViewById(R.id.bookName);
        TextView tvBookTown = (TextView) findViewById(R.id.bookTown);
        TextView tvBookCondition = (TextView) findViewById(R.id.bookCondition);
        TextView tvDescription = (TextView) findViewById(R.id.bookDescription);

        Intent intent = getIntent();
        Book book = intent.getParcelableExtra("book");

        tvBookName.setText(book.getTitle());
        tvBookTown.setText(book.getCity());
        tvBookCondition.setText(getResources().getStringArray(R.array.conditions)[book.getCondition()]);
        tvDescription.setText(book.getDescription());
        user_id = book.getOwnerID();

        Glide.with(mContext)
                .load(book.getCoverUrl())
                .into(imgBook);
        //TODO загрузка фото профиля, отправившего фотографию

    }

    private void loadUser() {
        User.findUserByID(user_id, new GetItemHandler<User>() {
            @Override
            public void done(User data) {
                /*Glide.with(mContext)
                        .load(data.getUserImage)
                        .into(imgProfile);*/
            }

            @Override
            public void error(String responseError) {
                Log.d("ERROR", responseError);
            }
        });
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_book;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.book_activity);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.done:
                Toast.makeText(this, "pressed", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

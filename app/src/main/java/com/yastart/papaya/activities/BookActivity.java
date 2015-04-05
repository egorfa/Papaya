package com.yastart.papaya.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.GetItemHandler;
import com.yastart.papaya.Model.Request;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.Model.VoidHandler;
import com.yastart.papaya.Papaya;
import com.yastart.papaya.R;

/**
 * Created by 123 on 05.04.2015.
 */
public class BookActivity extends BaseActivity {

    public static final String EXTRA_IS_CURRENT_USER_BOOK = "isCurrentUserBook";
    public static final String EXTRA_BOOK = "book";

    private String user_id;
    private ImageView imgProfile;
    private boolean isCurrentUserBook;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imgBook = (ImageView) findViewById(R.id.imgDescr);
        imgProfile = (ImageView) findViewById(R.id.imgUser);
        TextView tvBookName = (TextView) findViewById(R.id.bookName);
        tvBookName.setTypeface(Papaya.font_semibold);
        TextView tvBookTown = (TextView) findViewById(R.id.bookTown);
        TextView tvBookCondition = (TextView) findViewById(R.id.bookCondition);
        TextView tvDescription = (TextView) findViewById(R.id.bookDescription);
        tvDescription.setTypeface(Papaya.font_semibold);
        Button btnExchange = (Button)findViewById(R.id.btnExchange);

        Intent intent = getIntent();

        isCurrentUserBook = intent.getBooleanExtra(EXTRA_IS_CURRENT_USER_BOOK, false);

        book = intent.getParcelableExtra(EXTRA_BOOK);
        tvBookName.setText(book.getTitle());
        tvBookTown.setText(book.getCity());
        tvBookCondition.setText(getResources().getStringArray(R.array.conditions)[book.getCondition()-1]);
        tvDescription.setText(book.getDescription());
        user_id = book.getOwnerID();

        Glide.with(mContext)
                .load(book.getCoverUrl())
                .into(imgBook);
        //TODO загрузка фото профиля, отправившего фотографию

        btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request newRequest = new Request();
                newRequest.setInitiatorID(User.getCurrentUser().getId());
                newRequest.setResponderID(book.getOwnerID());
                newRequest.setBookDesiredID(book.getId());

                newRequest.save(new VoidHandler() {
                    @Override
                    public void done() {
                        Log.d("SAVED", "SAAAAVED!!!!");
                        Toast.makeText(BookActivity.this, "Запрос отправлен пользователю", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void error(String responseError) {
                        Log.d("ERROR", responseError);
                    }
                });
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCurrentUserBook) {
            getMenuInflater().inflate(R.menu.my_book_menu, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getString(R.string.share_body_text) + " " +
                        book.getTitle() + " " + getString(R.string.with_papaya);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.book_in_papaya));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

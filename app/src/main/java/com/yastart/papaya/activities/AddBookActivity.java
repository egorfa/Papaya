package com.yastart.papaya.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.Model.VoidHandler;
import com.yastart.papaya.R;
import com.yastart.papaya.dialogs.PhotoPickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AddBookActivity extends BaseActivity implements View.OnClickListener,
        PhotoPickerDialog.TakePhotoListener,
        ImageChooserListener {

    private ImageChooserManager imageChooserManager;
    private String filePath;
    private int chooserType;
    private ImageView bookPhoto;
    private boolean isImageUploaded = false;

    private EditText bookName;
    private EditText author;
    private EditText description;
    private Spinner conditionSpinner;

    private String URL;
    private String titleText;
    private String authorText;
    private String descriptionText;
    private int condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookName = (EditText) findViewById(R.id.book_name);
        author = (EditText) findViewById(R.id.author);
        description = (EditText) findViewById(R.id.description);

        bookPhoto = (ImageView) findViewById(R.id.book_photo);
        bookPhoto.setOnClickListener(this);

        conditionSpinner = (Spinner) findViewById(R.id.condition);
        String[] data = getResources().getStringArray(R.array.conditions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_book_menu, menu);
        return true;
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_add_book;
    }

    @Override
    protected String getTitleToolBar() {
        return getString(R.string.add_book);
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
                if (checkFields())
                    addBook();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addBook() {
        Book newBook = new Book();
        newBook.setAuthors(authorText);
        newBook.setTitle(titleText);
        newBook.setDescription(descriptionText);
        newBook.setCity("Moscow");
        newBook.setCoverUrl(URL);
        newBook.setCondition(condition);
//        newBook.setOwnerID(User.getCurrentUser().getId());
        newBook.setOwnerID("117211419728589565827");
        newBook.saveBook(new VoidHandler() {
            @Override
            public void done() {
                Log.d("TAG", "------------------> done");
            }

            @Override
            public void error(String responseError) {
                Toast.makeText(mContext, R.string.save_error, Toast.LENGTH_LONG).show();
            }
        });
        finish();
    }

    private boolean checkFields() {
        titleText = String.valueOf(bookName.getText()).trim();
        authorText = String.valueOf(author.getText()).trim();
        descriptionText = String.valueOf(description.getText()).trim();
        condition = conditionSpinner.getSelectedItemPosition() + 1;

        if (!isImageUploaded) {
            Toast.makeText(mContext, R.string.image_not_uploaded, Toast.LENGTH_LONG).show();
            return false;
        } else if (titleText.isEmpty() || authorText.isEmpty() || descriptionText.isEmpty()) {
            Toast.makeText(mContext, R.string.all_fields_required, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_photo:
                final DialogFragment dialog = new PhotoPickerDialog();
                dialog.show(getSupportFragmentManager(), "StopTimerDialog");
                break;
        }
    }

    @Override
    public void onTakePhotoClick() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this, ChooserType.REQUEST_CAPTURE_PICTURE, "Papaya", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShowGalleryClick() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this, ChooserType.REQUEST_PICK_PICTURE, "Papaya", true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        }
    }

    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)
    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, "Papaya", true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (image != null) {
                    filePath = image.getFilePathOriginal();
                    bookPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    File file = new File(image.getFilePathOriginal());
                    bookPhoto.setImageURI(Uri.parse(file.toString()));
                    byte[] imageBytes = getImageBytes(file);
                    if (imageBytes != null) {
                        final ParseFile photo = new ParseFile("book_cover.jpg", imageBytes);
                        photo.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    isImageUploaded = true;
                                    Log.d("TAG", "---------------->" + photo.getUrl());
                                    URL = photo.getUrl();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private byte[] getImageBytes(File file) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, false);
        byte[] imageBytes = null;
        ByteArrayOutputStream stream = null;
        try {
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageBytes = stream.toByteArray();
        } finally {
            if (stream != null) try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageBytes;
    }

    @Override
    public void onError(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "Error: " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.yastart.papaya.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.yastart.papaya.R;
import com.yastart.papaya.dialogs.PhotoPickerDialog;

import java.io.File;

public class AddBookActivity extends BaseActivity implements View.OnClickListener,
        PhotoPickerDialog.TakePhotoListener,
        ImageChooserListener {

    private ImageChooserManager imageChooserManager;
    private String filePath;
    private int chooserType;
    private ImageView bookPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final EditText bookName = (EditText) findViewById(R.id.book_name);

        final EditText author = (EditText) findViewById(R.id.author);

        final EditText description = (EditText) findViewById(R.id.description);

        bookPhoto = (ImageView) findViewById(R.id.book_photo);
        bookPhoto.setOnClickListener(this);

        final Spinner conditionSpinner = (Spinner) findViewById(R.id.condition);
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
                Toast.makeText(this, "pressed", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
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
                    bookPhoto.setImageURI(Uri.parse(new File(image.getFilePathOriginal()).toString()));
                    // TODO отправлять в хранилище
                }
            }
        });
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

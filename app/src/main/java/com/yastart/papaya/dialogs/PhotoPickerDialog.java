package com.yastart.papaya.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.yastart.papaya.R;

public class PhotoPickerDialog extends DialogFragment {

    private TakePhotoListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.book_cover)
                .setItems(R.array.chose_photo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mListener.onTakePhotoClick();
                                break;
                            case 1:
                                mListener.onShowGalleryClick();
                                break;
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TakePhotoListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    public interface TakePhotoListener {
        public void onTakePhotoClick();
        public void onShowGalleryClick();
    }
}

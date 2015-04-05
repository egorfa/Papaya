package com.yastart.papaya.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yastart.papaya.R;

/**
 * Created by 123 on 04.04.2015.
 */
public class DialogBookRequest extends DialogFragment implements View.OnClickListener {

    private Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_request, container);
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        context = rootView.getContext();

        final ImageView imgBook = (ImageView) rootView.findViewById(R.id.imgDialog);
        final TextView txtUser = (TextView) rootView.findViewById(R.id.dialogTxtUser);
        final TextView txtBook = (TextView) rootView.findViewById(R.id.dialogTxtBook);
        final TextView txtDescr = (TextView) rootView.findViewById(R.id.dialogTxtDescr);
        final Button btnDecline = (Button) rootView.findViewById(R.id.dialogDecline);
        final Button btnAccept = (Button) rootView.findViewById(R.id.dialogAccept);

        btnDecline.setOnClickListener(this);
        btnAccept.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}

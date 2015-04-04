package com.yastart.papaya.adapters.view_holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yastart.papaya.R;

/**
 * Created by Egor on 04.04.2015.
 */
public class FeedBackViewHolder {

    public ImageView profileImage;
    public TextView feedbackTv;

    public FeedBackViewHolder(View itemView, View.OnClickListener listener) {

        profileImage = (ImageView) itemView.findViewById(R.id.feedback_profile_img);
        feedbackTv = (TextView) itemView.findViewById(R.id.feedback_textview);

        itemView.setOnClickListener(listener);
    }
}

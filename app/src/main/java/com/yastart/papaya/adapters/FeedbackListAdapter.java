package com.yastart.papaya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.yastart.papaya.Model.FeedBack;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.view_holders.FeedBackViewHolder;

import java.util.ArrayList;

public class FeedbackListAdapter extends ArrayAdapter<FeedBack> implements View.OnClickListener {

    final Context mContext;
    ArrayList<FeedBack> feedbacks;

    public FeedbackListAdapter(Context context, ArrayList<FeedBack> feedbacks) {
        super(context, R.layout.item_profilefeedback_list, feedbacks);
        mContext = context;
        this.feedbacks = feedbacks;
    }


    public View getView(final int position, View convertView, final ViewGroup parent) {

        final FeedBack feedBack = feedbacks.get(position);

        FeedBackViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_profilefeedback_list, parent, false);

            holder = new FeedBackViewHolder(convertView, this);

            convertView.setTag(holder);
        } else {
            holder = (FeedBackViewHolder) convertView.getTag();
        }

        /*Glide.with(mContext)
                .load(feedBack.getProfileOwnerId())
                .into(holder.profileImage);*/
        //TODO загрузку картинки профиля, отправившего отзыв

        holder.feedbackTv.setText(feedBack.getFeedback());

        return convertView;
    }

    @Override
    public void onClick(View view) {

    }
}

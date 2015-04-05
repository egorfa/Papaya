package com.yastart.papaya.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yastart.papaya.Model.FeedBack;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.FeedbackListAdapter;

import java.util.ArrayList;

/**
 * Created by Egor on 04.04.2015.
 */
public class MyProfileFragment extends BaseFragment implements View.OnClickListener {

    TextView userName, userContacts;

    public static MyProfileFragment newInstance() {
        MyProfileFragment pageFragment = new MyProfileFragment();
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        mContext = view.getContext();

        userName = (TextView) view.findViewById(R.id.tv_name);
        userContacts = (TextView) view.findViewById(R.id.tv_contacts);

        User u = User.getCurrentUser();

        userName.setText(u.getUsername());
        userContacts.setText(u.getEmail());

        ArrayList<FeedBack> feedbacks = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            FeedBack feedback = new FeedBack(null, null, "Спасибо за книгу! Быстро договорились и обменялись. Качество книги соответствует указанному.");
            feedbacks.add(feedback);
        }

        ListView listFeedback = (ListView) view.findViewById(R.id.list_feedback);

        FeedbackListAdapter adapter = new FeedbackListAdapter(mContext, feedbacks);
        listFeedback.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}

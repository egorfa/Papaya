package com.yastart.papaya.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.yastart.papaya.Model.FeedBack;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.FeedbackListAdapter;

import java.util.ArrayList;

/**
 * Created by Egor on 04.04.2015.
 */
public class MyProfileFragment extends BaseFragment implements View.OnClickListener  {

    public static MyProfileFragment newInstance() {
        MyProfileFragment pageFragment = new MyProfileFragment();
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);



        ArrayList<FeedBack> feedbacks = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            FeedBack feedback = new FeedBack(null, null, "говно");
            feedbacks.add(feedback);
        }

        ListView listFeedback = (ListView) view.findViewById(R.id.list_feedback);

        FeedbackListAdapter adapter = new FeedbackListAdapter(getActivity().getBaseContext(), feedbacks);
        listFeedback.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}

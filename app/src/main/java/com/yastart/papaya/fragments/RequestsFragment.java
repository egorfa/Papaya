package com.yastart.papaya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.yastart.papaya.Model.GetListHandler;
import com.yastart.papaya.Model.Request;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.StickyLVAdapter;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by 123 on 05.04.2015.
 */
public class RequestsFragment extends BaseFragment {

    ArrayList<String> headings;
    ArrayList<Request> requests;

    public static RequestsFragment newInstance() {
        RequestsFragment pageFragment = new RequestsFragment();
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_requests, null);

        headings = new ArrayList<String>();
        requests = new ArrayList<Request>();

        User u = User.getCurrentUser();
        Request.getRequestsForUser(u, new GetListHandler<ArrayList<Request>>() {
            @Override
            public void done(ArrayList<ArrayList<Request>> data) {
                String tag = "REQUEST DEBUG";
                Log.d(tag, "Dimenstions " + data.size());
                Log.d(tag, "Dimenstion[0] " + data.get(0).size());
                Log.d(tag, "Dimenstions[1] " + data.get(1).size());
                for (int i = 0; i < data.get(1).size(); i++) {
                    headings.add("Входящие");
                    requests.add(data.get(1).get(i));
                }
                for (int i = 0; i < data.get(0).size(); i++) {
                    headings.add("Исходящие");
                    requests.add(data.get(0).get(i));
                }
            }

            @Override
            public void error(String responseError) {
                Log.d("REQUEST DEBUG", responseError);
            }
        });

        final StickyListHeadersListView exlv = (StickyListHeadersListView) view.findViewById(R.id.ex_lv);
        StickyLVAdapter adapter = new StickyLVAdapter(getActivity().getBaseContext(), headings, requests);
        exlv.setAdapter(adapter);


        exlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
            }
        });

        return view;
    }
}

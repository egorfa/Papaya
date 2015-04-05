package com.yastart.papaya.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.yastart.papaya.R;
import com.yastart.papaya.adapters.StickyLVAdapter;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by 123 on 05.04.2015.
 */
public class RequestsFragment extends BaseFragment {

    ArrayList<String> headings;
    ArrayList<String> requests;
    final int DIALOG=1;

    public static RequestsFragment newInstance() {
        RequestsFragment pageFragment = new RequestsFragment();
        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, null);

        headings = new ArrayList<String>();
        requests = new ArrayList<String>();

        headings.add("Входящие");
        headings.add("Входящие");
        headings.add("Исходящие");
        headings.add("Исходящие");
        headings.add("Исходящие");

        requests.add("Oxuenno");
        requests.add("Oxuenno");
        requests.add("Oxuenno");
        requests.add("Oxuenno");
        requests.add("Oxuenno");

        final StickyListHeadersListView exlv = (StickyListHeadersListView)view.findViewById(R.id.ex_lv);
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

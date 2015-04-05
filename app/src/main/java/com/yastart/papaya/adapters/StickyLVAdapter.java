package com.yastart.papaya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yastart.papaya.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by 123 on 26.02.2015.
 */
public class StickyLVAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<String> headings;
    private ArrayList<String> requests;
    private LayoutInflater inflater;

    public StickyLVAdapter(Context context, ArrayList<String> headings, ArrayList<String> requests) {
        this.inflater = LayoutInflater.from(context);
        this.headings = headings;
        this.requests = requests;
    }

    @Override
    public int getCount() {
        return headings.size();
    }

    @Override
    public String getItem(int position) {
        return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.expandable_listview_item, parent, false);
            //holder.text = (TextView) convertView.findViewById(R.id.lv_word);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.text.setText(mWords.get(position));

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.expandable_listview_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.headerTxt);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        holder.text.setText(headings.get(position));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return position;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        //TextView text;
    }

}

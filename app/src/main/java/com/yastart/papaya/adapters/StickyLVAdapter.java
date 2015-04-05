package com.yastart.papaya.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yastart.papaya.Model.GetItemHandler;
import com.yastart.papaya.Model.Request;
import com.yastart.papaya.Model.User;
import com.yastart.papaya.Papaya;
import com.yastart.papaya.R;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by 123 on 26.02.2015.
 */
public class StickyLVAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private static String fromUser = "Вы отправили запрос на обмен книгой пользователю ";
    private static String toUser = " предлагает вам обменяться книгой";

    private ArrayList<String> headings = new ArrayList<String>();
    private ArrayList<Request> requests = new ArrayList<Request>();
    private LayoutInflater inflater;

    public StickyLVAdapter(Context context, ArrayList<String> headings, ArrayList<Request> requests) {
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
        return headings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.expandable_listview_item, parent, false);
            holder.msg = (TextView) convertView.findViewById(R.id.lv_message);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User u = User.getCurrentUser();
        Request request = requests.get(position);

        if(request.getInitiatorID().equals(u.getId())){
            User.findUserByID(request.getInitiatorID(), new GetItemHandler<User>() {
                @Override
                public void done(User data) {
                    holder.msg.setText(fromUser + data.getUsername());
                }

                @Override
                public void error(String responseError) {

                }
            });

        }
        else {
            User.findUserByID(request.getInitiatorID(), new GetItemHandler<User>() {
                @Override
                public void done(User data) {
                    holder.msg.setText("Пользователь " + data.getUsername() + toUser);
                }

                @Override
                public void error(String responseError) {
                    Log.d("TEST", responseError);
                }
            });
        }

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.expandable_listview_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.headerTxt);
            holder.text.setTypeface(Papaya.font_semibold);
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
        return headings.get(position).subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView msg;
    }

}

package com.yastart.papaya.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yastart.papaya.R;

public class MarketViewHolder extends RecyclerView.ViewHolder {

    public TextView bookName;
    public TextView author;
    public TextView price;

    public MarketViewHolder(View itemView, View.OnClickListener listener) {
        super(itemView);

        bookName = (TextView) itemView.findViewById(R.id.book_name);
        author = (TextView) itemView.findViewById(R.id.author);
        price = (TextView) itemView.findViewById(R.id.price);

        itemView.setOnClickListener(listener);
    }
}

package com.yastart.papaya.adapters.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yastart.papaya.Papaya;
import com.yastart.papaya.R;

public class BooksGridViewHolder extends RecyclerView.ViewHolder {
    public ImageView bookImage;
    public LinearLayout textBackground;
    public TextView bookName;
    public TextView author;


    public BooksGridViewHolder(View itemView, View.OnClickListener listener) {
        super(itemView);

        bookImage = (ImageView) itemView.findViewById(R.id.book_image);
        textBackground = (LinearLayout) itemView.findViewById(R.id.text_background);
        bookName = (TextView) itemView.findViewById(R.id.book_name);
        bookName.setTypeface(Papaya.font_semibold);
        author = (TextView) itemView.findViewById(R.id.author);

        itemView.setOnClickListener(listener);
    }
}

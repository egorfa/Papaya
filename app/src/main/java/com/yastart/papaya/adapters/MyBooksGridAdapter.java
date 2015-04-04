package com.yastart.papaya.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yastart.papaya.Model.Book;
import com.yastart.papaya.R;
import com.yastart.papaya.adapters.view_holders.BooksGridViewHolder;

import java.util.ArrayList;

public class MyBooksGridAdapter extends RecyclerView.Adapter<BooksGridViewHolder> {

    private final Context mContext;
    private ArrayList<Book> books;
    private final View.OnClickListener listener;

    public MyBooksGridAdapter(final Context context, final ArrayList<Book> books, final View.OnClickListener listener) {
        mContext = context;
        this.books = books;
        this.listener = listener;
    }

    @Override
    public BooksGridViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book_grid, viewGroup, false);
        return new BooksGridViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(final BooksGridViewHolder viewHolder, int i) {
        final Book book = books.get(i);
        Glide.with(mContext)
                .load(book.getCoverUrl())
                .into(viewHolder.bookImage);
        viewHolder.bookName.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthors());

        Glide.with(mContext)
                .load(book.getCoverUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch vibrant = palette.getVibrantSwatch();
                                if (vibrant != null) {
                                    viewHolder.textBackground.setBackgroundColor(vibrant.getRgb());
                                    viewHolder.bookName.setTextColor(vibrant.getTitleTextColor());
                                    viewHolder.author.setTextColor(vibrant.getTitleTextColor());
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}

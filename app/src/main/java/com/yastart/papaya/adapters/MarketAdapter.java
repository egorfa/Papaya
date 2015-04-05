package com.yastart.papaya.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yastart.papaya.R;
import com.yastart.papaya.activities.SearchActivity;
import com.yastart.papaya.adapters.view_holders.MarketViewHolder;

public class MarketAdapter extends RecyclerView.Adapter<MarketViewHolder> {

    private final int index;
    private final View.OnClickListener listener;

    public MarketAdapter(final int index, final View.OnClickListener listener) {
        this.index = index;
        this.listener = listener;
    }

    @Override
    public MarketViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_market_card, viewGroup, false);
        return new MarketViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(MarketViewHolder holder, int position) {
        holder.bookName.setText(SearchActivity.MarketBooks.titles[index]);
        holder.author.setText(SearchActivity.MarketBooks.authors[index]);
        holder.price.setText(SearchActivity.MarketBooks.prices[index]);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

package com.akimi808.quotescollector;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by akimi808 on 02/12/16.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {
    private QuoteManager quoteManager;

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(QuoteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return quoteManager.getQuoteCount();
    }

    public static class QuoteViewHolder extends RecyclerView.ViewHolder {

        public QuoteViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.akimi808.quotescollector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akimi808.quotescollector.model.Quote;

/**
 * Created by akimi808 on 02/12/16.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {
    private QuoteManager quoteManager;

    public QuoteAdapter(QuoteManager quoteManager) {
        this.quoteManager = quoteManager;
    }

    //метод вызывается, когда RecyclerView нуждается еще одном экз. View элемента
    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.quote_card_item, parent, false);
        return new QuoteViewHolder(view);
    }
    //помещает данные внутрь view
    @Override
    public void onBindViewHolder(QuoteViewHolder holder, int position) {
        Quote quote = quoteManager.getQuoteByIndex(position);
        holder.bind(quote);
    }

    @Override
    public int getItemCount() {
        return quoteManager.getQuoteCount();
    }

    public static class QuoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView quoteText;
        private final TextView authorText;
        private final TextView sourceText;

        public QuoteViewHolder(View itemView) {
            super(itemView);
            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            authorText = (TextView) itemView.findViewById(R.id.quoteAuthor);
            sourceText = (TextView) itemView.findViewById(R.id.quoteSource);
        }

        public void bind(Quote quote) {
            quoteText.setText(quote.getText());
            authorText.setText(quote.getAuthor());
            sourceText.setText(quote.getSource());
        }
    }
}

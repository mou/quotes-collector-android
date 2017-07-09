package com.akimi808.quotescollector.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akimi808.quotescollector.QuoteManager;
import com.akimi808.quotescollector.R;

import com.akimi808.quotescollector.fragments.AuthorFragment.OnAuthorFragmentInteractionListener;
import com.akimi808.quotescollector.model.Author;

public class AuthorRecyclerViewAdapter extends RecyclerView.Adapter<AuthorRecyclerViewAdapter.ViewHolder> implements QuoteManager.DataChangedListener {

    private QuoteManager quoteManager;
    private final OnAuthorFragmentInteractionListener listener;

    public AuthorRecyclerViewAdapter(QuoteManager quoteManager, OnAuthorFragmentInteractionListener listener) {
        this.quoteManager = quoteManager;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_author, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Author author = quoteManager.getAuthorByIndex(position);
        holder.author = author;
        holder.idView.setText(author.getId().toString());
        holder.authorName.setText(author.getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onAuthorClicked(holder.author);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return quoteManager.getAuthorCount();
    }

    @Override
    public void onDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        quoteManager.registerForDataChanged(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        quoteManager.deregisterForDataChanged(this);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView idView;
        private final TextView authorName;
        private Author author;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.id);
            authorName = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + authorName.getText() + "'";
        }
    }
}

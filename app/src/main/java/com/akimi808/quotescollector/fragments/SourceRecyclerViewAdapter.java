package com.akimi808.quotescollector.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akimi808.quotescollector.QuoteManager;
import com.akimi808.quotescollector.R;


import com.akimi808.quotescollector.fragments.SourceFragment.OnSourceFragmentInteractionListener;
import com.akimi808.quotescollector.fragments.dummy.DummyContent.DummyItem;
import com.akimi808.quotescollector.model.Source;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnSourceFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SourceRecyclerViewAdapter extends RecyclerView.Adapter<SourceRecyclerViewAdapter.ViewHolder> implements QuoteManager.DataChangedListener {

    private QuoteManager quoteManager;
    private final OnSourceFragmentInteractionListener listener;

    public SourceRecyclerViewAdapter(QuoteManager quoteManager, OnSourceFragmentInteractionListener listener) {
        this.quoteManager = quoteManager;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_source, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Source source = quoteManager.getSourceByIndex(position);
        holder.source = source;
        holder.idView.setText(source.getId().toString());
        holder.titleView.setText(source.getTitle());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onSourceClicked(holder.source);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return quoteManager.getSourceCount();
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
        private final TextView titleView;
        private Source source;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            idView = (TextView) view.findViewById(R.id.id);
            titleView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }
    }
}

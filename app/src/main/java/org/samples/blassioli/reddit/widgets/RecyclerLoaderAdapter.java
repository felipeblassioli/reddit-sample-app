package org.samples.blassioli.reddit.widgets;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.samples.blassioli.reddit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class RecyclerLoaderAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_ITEM = 1;

    private final int VIEW_PROG = 0;

    public abstract T getItem(int position);

    public abstract  RecyclerView.ViewHolder getDataViewHolder(ViewGroup parent);

    protected abstract void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position);

    public ProgressViewHolder getProgressViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.default_progress_item, parent, false);
       return new ProgressViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            vh = getDataViewHolder(parent);
        } else {
            vh = getProgressViewHolder(parent);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_ITEM) {
            onBindDataViewHolder(holder, position);
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_bar) public ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

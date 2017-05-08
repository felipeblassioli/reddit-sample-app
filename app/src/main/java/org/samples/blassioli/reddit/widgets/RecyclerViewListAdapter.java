package org.samples.blassioli.reddit.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class RecyclerViewListAdapter<T extends List, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    protected T items;

    public RecyclerViewListAdapter(T items) {
        super();
        this.items = items;
    }

    @Override public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public void addAll(T items) {
        this.items.addAll(items);
    }

}

package org.samples.blassioli.reddit.widgets;

import java.util.List;

public abstract class RecyclerViewListAdapter<T> extends RecyclerLoaderAdapter<T> {

    private List<T> items;

    public RecyclerViewListAdapter(List<T> items) {
        super();
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items == null ? 0 : this.items.size();
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T>  items) {
        this.items = items;
    }

    public void add(T item) {
        this.items.add(item);
    }

    public void addAll(List<T> items) {
        this.items.addAll(items);
    }

    public T getItem(int position) {
        return this.items.get(position);
    }

    public void removeLast() {
        if(getItemCount() > 0) {
            this.items.remove(getItemCount() - 1);
        }
    }

    public T peekLast() {
       return getItemCount() > 0? this.items.get(getItemCount() - 1) : null;
    }

}

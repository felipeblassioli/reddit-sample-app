package org.samples.blassioli.reddit.features.posts;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.samples.blassioli.reddit.features.posts.widgets.Link;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerViewListAdapter<Post> {
    private final Activity activity;

    public PostsAdapter(Activity activity) {
        this(activity, new ArrayList<>());
    }

    public PostsAdapter(Activity activity, List<Post> items) {
        super(items);
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder getDataViewHolder(ViewGroup parent) {
        View v = new Link(parent.getContext(), activity);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindDataViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ViewHolder holder = (ViewHolder) rawHolder;
        holder.link.setData(getItems().get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Link link;

        public ViewHolder(View view) {
            super(view);
            this.link = (Link) view;
        }
    }
}

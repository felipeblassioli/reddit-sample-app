package org.samples.blassioli.reddit.features.details.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.samples.blassioli.reddit.features.details.widgets.Comment;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerViewListAdapter<CommentModel> {

    public DetailsAdapter() {
        this(new ArrayList<>());
    }

    public DetailsAdapter(List<CommentModel> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder getDataViewHolder(ViewGroup parent) {
        View v = new Comment(parent.getContext());
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindDataViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ViewHolder holder = (ViewHolder) rawHolder;
        holder.comment.setData(getItems().get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Comment comment;

        public ViewHolder(View view) {
            super(view);
            comment = (Comment) view;
        }
    }
}

package org.samples.blassioli.reddit.features.details.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;
import org.samples.blassioli.reddit.widgets.RedditHeadline;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsAdapter extends RecyclerViewListAdapter<DetailsItem> {

    public DetailsAdapter() {
        this(new ArrayList<>());
    }

    public DetailsAdapter(List<DetailsItem> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder getDataViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    protected void onBindDataViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ViewHolder holder = (ViewHolder) rawHolder;
        DetailsItem item = getItems().get(position);

        holder.headline.setAuthor(item.author);
        holder.headline.setCreatedUtc(item.createdUtc);
        holder.body.setText(item.body);
        holder.ups.setText(item.ups);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headline)
        public RedditHeadline headline;

        @BindView(R.id.body_text)
        public TextView body;

        @BindView(R.id.ups)
        public TextView ups;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

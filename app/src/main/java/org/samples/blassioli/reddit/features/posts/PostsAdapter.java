package org.samples.blassioli.reddit.features.posts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.DetailsActivity;
import org.samples.blassioli.reddit.picasso.RoundedTransformation;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;
import org.samples.blassioli.reddit.widgets.RedditHeadline;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    protected void onBindDataViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ViewHolder holder = (ViewHolder) rawHolder;
        Post p = getItems().get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailsActivity.class);
                Bundle b = new Bundle();
                b.putString(DetailsActivity.P_LINK_ID, p.id);
                intent.putExtras(b);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
            }
        });

        holder.title.setText(p.title);
        holder.headline.setAuthor(p.author);
        holder.headline.setCreatedUtc(p.createdUtc);

        if (p.thumbnail != null &&
                !TextUtils.isEmpty(p.thumbnail) &&
                URLUtil.isValidUrl(p.thumbnail)) {
            holder.thumbnail.setVisibility(View.VISIBLE);
            Picasso.with(activity)
                    .load(p.thumbnail)
                    .fit()
                    .centerCrop()
                    .transform(new RoundedTransformation(16, 16))
                    .into(holder.thumbnail);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = .3f;
            holder.contentGroup.setLayoutParams(params);
        } else {
            holder.thumbnail.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 0f;
            holder.contentGroup.setLayoutParams(params);
        }
        holder.body.setText(p.selftext);
        holder.ups.setText(p.ups);
        holder.totalComments.setText(p.numComments);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content_layout)
        public ViewGroup contentGroup;

        @BindView(R.id.headline)
        public RedditHeadline headline;

        @BindView(R.id.title_text)
        public TextView title;

        @BindView(R.id.img_thumbnail)
        public ImageView thumbnail;

        @BindView(R.id.body_text)
        public TextView body;

        @BindView(R.id.ups)
        public TextView ups;

        @BindView(R.id.total_comments)
        public TextView totalComments;

        @BindView(R.id.card_view)
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

package org.samples.blassioli.reddit.features.posts;

import android.content.Context;
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
import org.samples.blassioli.reddit.picasso.RoundedTransformation;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerViewListAdapter<List<Post>, PostsAdapter.ViewHolder> {
    private final Context context;

    public PostsAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public PostsAdapter(Context context, List<Post> items) {
        super(items);
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content_layout) public ViewGroup contentGroup;
        @BindView(R.id.author_text) public TextView author;
        @BindView(R.id.created_text) public TextView created;
        @BindView(R.id.title_text) public TextView title;
        @BindView(R.id.img_thumbnail) public ImageView thumbnail;
        @BindView(R.id.body_text) public TextView body;
        @BindView(R.id.ups) public TextView ups;
        @BindView(R.id.total_comments) public TextView totalComments;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post p = getItems().get(position);
        holder.title.setText(p.title);
        String formattedAuthor = String.format("u/%s \u2022 ", p.author);
        holder.author.setText(formattedAuthor);

        //1494198768 - 05/07/2017 @ 11:12pm (UTC)
        double now = System.currentTimeMillis() / 1000.0;
        Long ts = p.createdUtc;
        int diff = (int)(now - ts);
        String qualifier = "m";
        diff /= 60;
        if(diff >= 60) {
            qualifier = "h";
            diff /= 60;
        }
        if(diff >= 24) {
            qualifier = "d";
            diff /= 24;
        }
        if(diff >= 24) {
            qualifier = "w";
            diff /= 24;
        }
        String formattedCreated = String.format("%d%s", diff, qualifier);
        holder.created.setText(formattedCreated);

        if(p.thumbnail != null &&
                !TextUtils.isEmpty(p.thumbnail) &&
                URLUtil.isValidUrl(p.thumbnail)) {
            holder.thumbnail.setVisibility(View.VISIBLE);
            Picasso.with(context)
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

}

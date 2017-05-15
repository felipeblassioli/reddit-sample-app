package org.samples.blassioli.reddit.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
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
import org.samples.blassioli.reddit.features.posts.Post;
import org.samples.blassioli.reddit.picasso.RoundedTransformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedditLink extends CardView {
    private Activity activity;

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

    public RedditLink(Context context) {
        super(context);
        init(context);
    }

    public RedditLink(Context context, Activity activity) {
        super(context);
        this.activity = activity;
        init(context);
    }

    public RedditLink(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RedditLink(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.post_item_card, this, true);
        ButterKnife.bind(this);
    }

    public void setThumbnail(String thumbnailUrl) {
        Context context = getContext();

        if (thumbnailUrl != null &&
                !TextUtils.isEmpty(thumbnailUrl) &&
                URLUtil.isValidUrl(thumbnailUrl)) {
            thumbnail.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(thumbnailUrl)
                    .fit()
                    .centerCrop()
                    .transform(new RoundedTransformation(16, 16))
                    .into(thumbnail);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = .3f;
            contentGroup.setLayoutParams(params);
        } else {
            thumbnail.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 0f;
            contentGroup.setLayoutParams(params);
        }
    }

    public void setData(Post p) {
       cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DetailsActivity.createIntent(activity, p.id, p);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
            }
        });

        title.setText(p.title);
        headline.setAuthor(p.author);
        headline.setCreatedUtc(p.createdUtc);

        setThumbnail(p.thumbnail);

        body.setText(p.selftext);
        ups.setText(p.ups);
        totalComments.setText(p.numComments);
    }
}

package org.samples.blassioli.reddit.widgets;

import android.content.Context;
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
import org.samples.blassioli.reddit.features.posts.model.Post;
import org.samples.blassioli.reddit.picasso.RoundedTransformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedditLink extends CardView {
    @BindView(R.id.content_container)
    public ViewGroup contentContainer;

    @BindView(R.id.content_layout)
    public ViewGroup contentGroup;
    @BindView(R.id.headline)
    public RedditHeadline headline;
    @BindView(R.id.title_text)
    public TextView title;
    @BindView(R.id.img_thumbnail)
    public ImageView thumbnail;
    @BindView(R.id.subtitle_text)
    public TextView subtitleText;
    @BindView(R.id.ups)
    public TextView ups;
    @BindView(R.id.total_comments)
    public TextView totalComments;
    @BindView(R.id.card_view)
    public CardView cardView;

/*    @BindView(R.id.external_url)
    public ExternalUrl url;*/

    public RedditLink(Context context) {
        super(context);
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

    public void setData(Post model) {
        //url.setVisibility(View.GONE);
        contentContainer.setVisibility(View.GONE);
        title.setText(model.title);
        headline.setAuthor(model.author);
        headline.setCreatedUtc(model.createdUtc);

        setThumbnail(model.thumbnail);

        subtitleText.setText(getFormatedSubtitleText(model));
        ups.setText(model.ups);
        totalComments.setText(model.numComments);
    }

    protected String getFormatedSubtitleText(Post p) {
        // TODO: Estimate the text size on the screen and take first 3 or 4 lines
        String text = p.selftext;
        return text.length() >= 256? text.substring(0, 255) : text;
    }

}

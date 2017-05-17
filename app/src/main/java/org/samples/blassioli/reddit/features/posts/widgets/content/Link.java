package org.samples.blassioli.reddit.features.posts.widgets.content;

import android.content.Context;
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

public class Link extends LinearLayout {
    @BindView(R.id.content_layout)
    public ViewGroup contentGroup;
    @BindView(R.id.title_text)
    public TextView title;
    @BindView(R.id.img_thumbnail)
    public ImageView thumbnail;
    @BindView(R.id.subtitle_text)
    public TextView subtitle;

    public Link(Context context) {
        super(context);
        init(context);
    }

    public Link(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Link(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.post_item_card_header, this, true);
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
        this.title.setText(model.title);
        this.setThumbnail(model.thumbnail);
        this.subtitle.setVisibility(View.GONE);
    }
}

package org.samples.blassioli.reddit.features.details.widgets.content;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.posts.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Image extends LinearLayout {
    @BindView(R.id.preview_image)
    public ImageView previewImage;

    public Image(Context context) {
        super(context);
        init(context);
    }

    public Image(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Image(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_reddit_link_body_image, this, true);
        ButterKnife.bind(this);
    }

    public void setPreviewImage(String imageUrl) {
        Context context = getContext();
        Picasso.with(context)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(previewImage);
    }

    public void setData(Post model) {
        String imageUrl = selectBestImageResolution(model);
        this.setPreviewImage(imageUrl);
    }

    private String selectBestImageResolution(Post model) {
        return model.preview.images.get(0).source.url;
    }
}

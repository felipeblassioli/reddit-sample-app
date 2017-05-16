package org.samples.blassioli.reddit.features.details.widgets.content;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.posts.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Link extends LinearLayout {

    @BindView(R.id.url_text)
    public TextView url;

    @BindView(R.id.preview_image)
    public ImageView previewImage;

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

    protected void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_reddit_link_body_link, this, true);
        ButterKnife.bind(this);
    }

    protected String getFormatedUrl(String url) {
        return url;
    }

    public void setUrl(String url) {
        this.url.setText(getFormatedUrl(url));
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
        this.setUrl(model.domain);
        String imageUrl = selectBestImageResolution(model);
        this.setPreviewImage(imageUrl);
    }

    private String selectBestImageResolution(Post model) {
        // TODO: Probably gotta check if the urls are realiable
        // They were not for: https://www.reddit.com/r/Android/comments/6bdfqr/.json?depth=4&limit=20
/*        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        List<PreviewDataImageSource> candidates = model.preview.images.get(0).resolutions;
        PreviewDataImageSource bestCandidate = candidates.get(0);
        //return bestCandidate.url;*/
        return model.preview.images.get(0).source.url;
    }

}

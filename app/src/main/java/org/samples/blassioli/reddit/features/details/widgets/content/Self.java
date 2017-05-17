package org.samples.blassioli.reddit.features.details.widgets.content;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.posts.model.Post;
import org.samples.blassioli.reddit.widgets.MarkdownView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Self extends LinearLayout {
    @BindView(R.id.content_text)
    public MarkdownView contentText;

    public Self(Context context) {
        super(context);
        init(context);
    }

    public Self(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Self(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_reddit_link_body_self, this, true);
        ButterKnife.bind(this);
        contentText.loadMarkdown("dummy", "file:///android_asset/widget_comment.css");
    }

    public void setData(Post model) {
        contentText.loadMarkdown(model.selftext, "file:///android_asset/widget_comment.css");
        contentText.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                Log.d("XXX", "URL"+ url);
            }
        });
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT);
//        contentText.setLayoutParams(lp);
//        this.addView(contentText);
    }
}

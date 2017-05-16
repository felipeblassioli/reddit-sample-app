package org.samples.blassioli.reddit.features.details.widgets.content;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.posts.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Self extends LinearLayout {
    @BindView(R.id.content_text)
    public TextView contentText;

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
    }

    public void setData(Post model) {
        contentText.setText(model.selftext);
    }
}

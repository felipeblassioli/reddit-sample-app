package org.samples.blassioli.reddit.widgets.reddit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.posts.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkFooter extends LinearLayout {
    @BindView(R.id.ups_text)
    public TextView ups;
    @BindView(R.id.total_comments_text)
    public TextView totalComments;

    public LinkFooter(Context context) {
        super(context);
        init(context);
    }

    public LinkFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinkFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_link_footer, this, true);
        ButterKnife.bind(this);
    }

    public void setData(Post model) {
        this.ups.setText(model.ups);
        this.totalComments.setText(model.numComments);
    }
}

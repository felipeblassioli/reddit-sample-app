package org.samples.blassioli.reddit.features.posts.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.samples.blassioli.reddit.features.details.widgets.content.Image;
import org.samples.blassioli.reddit.features.posts.model.Post;
import org.samples.blassioli.reddit.features.posts.widgets.content.Link;
import org.samples.blassioli.reddit.features.posts.widgets.content.Self;

public class LinkBody extends RelativeLayout {

    public LinkBody(Context context) {
        super(context);
    }

    public LinkBody(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkBody(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setData(Post model) {
        this.removeAllViews();
        String hint = model.postHint == null? "" : model.postHint;
        View contentView = null;
        switch (hint.toLowerCase()) {
            case "link":
                Link linkContent = new Link(getContext());
                linkContent.setData(model);
                contentView = linkContent;
                break;
            case "rich:video":
            case "image":
                Image imageContent = new Image(getContext());
                imageContent.setData(model);
                contentView = imageContent;
                break;
            case "self":
            default:
                Self selfContent = new Self(getContext());
                selfContent.setData(model);
                contentView = selfContent;
                break;
        }
        if(contentView != null) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            contentView.setLayoutParams(lp);
            this.addView(contentView);
        }
    }
}

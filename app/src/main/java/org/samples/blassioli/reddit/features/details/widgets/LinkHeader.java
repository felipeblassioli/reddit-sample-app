package org.samples.blassioli.reddit.features.details.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import org.samples.blassioli.reddit.features.posts.model.Post;
import org.samples.blassioli.reddit.widgets.reddit.BaseLink;

public class LinkHeader extends BaseLink {

    public LinkHeader(Context context) {
        super(context);
    }

    public LinkHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View createBody(Context context) {
        return new LinkHeaderBody(context);
    }

    @Override
    protected void onBodyBind(View view, Post model) {
        ((LinkHeaderBody) view).setData(model);
    }

}

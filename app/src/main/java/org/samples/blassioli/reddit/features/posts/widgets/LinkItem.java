package org.samples.blassioli.reddit.features.posts.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.DetailsActivity;
import org.samples.blassioli.reddit.features.posts.model.Post;
import org.samples.blassioli.reddit.widgets.reddit.BaseLink;

public class LinkItem extends BaseLink {

    private Activity activity;

    public LinkItem(Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    public LinkItem(Context context) {
        super(context);
    }

    public LinkItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View createBody(Context context) {
        return new LinkBody(context);
    }

    @Override
    protected void onBodyBind(View view, Post model) {
        ((LinkBody) view).setData(model);
        this.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DetailsActivity.createIntent(activity, model.id, model);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_up, R.anim.no_change);
            }
        });
    }

}

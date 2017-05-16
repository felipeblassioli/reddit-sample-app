package org.samples.blassioli.reddit.features.posts.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.DetailsActivity;
import org.samples.blassioli.reddit.features.posts.model.Post;
import org.samples.blassioli.reddit.widgets.RedditLink;

public class Link extends RedditLink {

    private Activity activity;

    public Link(Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    public Link(Context context) {
        super(context);
    }

    public Link(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Link(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setData(Post model) {
        super.setData(model);
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

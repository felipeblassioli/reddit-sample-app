package org.samples.blassioli.reddit.widgets.reddit;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.posts.model.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseLink extends CardView {
    @BindView(R.id.card_view)
    public CardView cardView;

    @BindView(R.id.card_header)
    ViewGroup headerContainer;

    @BindView(R.id.card_body)
    ViewGroup bodyContainer;

    @BindView(R.id.card_footer)
    ViewGroup footerContainer;

    private View header;

    private View body;

    private View footer;

    public BaseLink(Context context) {
        super(context);
        init(context);
    }

    public BaseLink(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseLink(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_reddit_link, this, true);
        ButterKnife.bind(this);

        header = createHeader(context);
        if(header != null) {
            this.headerContainer.addView(header);
        } else {
            this.headerContainer.setVisibility(View.GONE);
        }
        body = createBody(context);
        if(body != null) {
            this.bodyContainer.addView(body);
        } else {
            this.bodyContainer.setVisibility(View.GONE);
        }
        footer = createFooter(context);
        if(footer != null) {
            this.footerContainer.addView(footer);
        } else {
            this.footerContainer.setVisibility(View.GONE);
        }
    }

    protected View createHeader(Context context) {
        return new RedditHeadline(context);
    }

    protected View createBody(Context context) {
        return null;
    }

    protected View createFooter(Context context) {
        return new LinkFooter(context);
    }

    public void setData(Post model) {
        this.onHeaderBind(this.header, model);
        this.onBodyBind(this.body, model);
        this.onFooterBind(this.footer, model);
    }

    protected void onHeaderBind(View view, Post model) {
        ((RedditHeadline) view).setData(model);
    }

    protected void onBodyBind(View view, Post model) {
    }

    protected void onFooterBind(View view, Post model) {
        ((LinkFooter) view).setData(model);
    }

}


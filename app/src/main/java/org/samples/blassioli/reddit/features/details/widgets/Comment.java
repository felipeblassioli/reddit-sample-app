package org.samples.blassioli.reddit.features.details.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.model.CommentModel;
import org.samples.blassioli.reddit.widgets.RedditHeadline;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.feras.mdv.MarkdownView;

public class Comment extends LinearLayout {

    @BindView(R.id.margin_container)
    public ViewGroup marginContainer;

    @BindView(R.id.header_container)
    public ViewGroup headerContainer;

    @BindView(R.id.footer_container)
    public ViewGroup footerContainer;

    @BindView(R.id.body_container)
    public ViewGroup bodyContainer;

    @BindView(R.id.headline)
    public RedditHeadline headline;

    @BindView(R.id.subtitle_text)
    public MarkdownView body;

    @BindView(R.id.ups)
    public TextView ups;

    public Comment(Context context) {
        super(context);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_reddit_comment, this, true);
        ButterKnife.bind(this);
    }

    public void setDepth(int depth) {
        marginContainer.removeAllViews();
        VerticalLineBuilder verticalLineBuilder = new VerticalLineBuilder(getContext());
        for (int i = 0; i < depth; i++) {
            marginContainer.addView(verticalLineBuilder.create());
        }
    }

    public void setData(CommentModel model) {
        if (model.kind.equals("more")) {
            this.headerContainer.setVisibility(View.GONE);
            this.footerContainer.setVisibility(View.GONE);
            this.body.loadMarkdown("MORE");
        } else {
            this.headerContainer.setVisibility(View.VISIBLE);
            this.footerContainer.setVisibility(View.VISIBLE);
            this.headline.setCreatedUtc(model.createdUtc);
            this.headline.setAuthor(model.author);
            this.body.loadMarkdown(model.body, "file:///android_asset/widget_comment.css");
            this.ups.setText("" + model.ups);
        }
        this.setDepth(model.depth + 1);
    }

    protected class VerticalLineBuilder {
        private final Context context;
        private final Resources r;
        private final int backgroundColor;
        private final LayoutParams params;

        public VerticalLineBuilder(Context context) {
            this.context = context;
            this.r = context.getResources();
            this.backgroundColor = ContextCompat.getColor(context, R.color.material_grey_300);
            this.params = new LayoutParams(toPixel(1), LayoutParams.MATCH_PARENT);
            this.params.setMargins(toPixel(8), 0, 0, 0);
        }

        public View create() {
            View v = new View(context);
            v.setBackgroundColor(backgroundColor);
            v.setLayoutParams(params);
            return v;
        }

        private int toPixel(int dp) {
            return (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    r.getDisplayMetrics()
            );
        }
    }
}

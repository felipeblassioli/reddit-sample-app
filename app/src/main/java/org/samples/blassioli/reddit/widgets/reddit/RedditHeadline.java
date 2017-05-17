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

public class RedditHeadline extends LinearLayout {
    @BindView(R.id.author_text)
    TextView author;
    @BindView(R.id.created_text)
    TextView created;
    @BindView(R.id.kind_text)
    TextView kind;

    public RedditHeadline(Context context) {
        super(context);
        init(context);
    }

    public RedditHeadline(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RedditHeadline(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_reddit_headline, this, true);
        ButterKnife.bind(this);
    }

    public void setAuthor(String author) {
        String formattedAuthor = RedditUtils.formattedAuthor(author);
        this.author.setText(formattedAuthor);
    }

    public void setCreatedUtc(Long createdUtc) {
        String formattedCreated = RedditUtils.formattedCreatedTimestamp(createdUtc);
        this.created.setText(formattedCreated);
    }

    public void setKind(String rawKind) {
        if(rawKind == null) {
            this.kind.setText("");
            return;
        }
        String kind = rawKind;
        switch (rawKind) {
            case "rich:video":
                kind = "video";
                break;
        }
        String formattedKind = String.format(" \u2022 %s", kind);
        this.kind.setText(formattedKind);
    }

    public void setData(Post model) {
        this.setAuthor(model.author);
        this.setCreatedUtc(model.createdUtc);
        this.setKind(model.postHint);
    }

    private final static class RedditUtils {
        public static String formattedAuthor(String author) {
            return String.format("u/%s \u2022 ", author);
        }

        public static String formattedCreatedTimestamp(Long ts) {
            //1494198768 - 05/07/2017 @ 11:12pm (UTC)
            double now = System.currentTimeMillis() / 1000.0;
            int diff = (int) (now - ts);
            String qualifier = "m";
            diff /= 60;
            if (diff >= 60) {
                qualifier = "h";
                diff /= 60;
            }
            if (diff >= 24) {
                qualifier = "d";
                diff /= 24;
            }
            if (diff >= 24) {
                qualifier = "w";
                diff /= 24;
            }
            return String.format("%d%s", diff, qualifier);
        }
    }

}

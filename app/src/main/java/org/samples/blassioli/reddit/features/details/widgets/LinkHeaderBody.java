package org.samples.blassioli.reddit.features.details.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.widgets.content.Image;
import org.samples.blassioli.reddit.features.details.widgets.content.Link;
import org.samples.blassioli.reddit.features.details.widgets.content.Self;
import org.samples.blassioli.reddit.features.posts.model.Post;

public class LinkHeaderBody extends RelativeLayout {
    private String chromeTabUrl;

    private CustomTabsIntent chromeTabIntent;

    private OnClickListener openCustomChromeTab = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (chromeTabUrl != null) {
                chromeTabIntent.launchUrl(getContext(), Uri.parse(chromeTabUrl));
            }
        }
    };

    public LinkHeaderBody(Context context) {
        super(context);
        createAndSetTabIntent();
    }

    public LinkHeaderBody(Context context, AttributeSet attrs) {
        super(context, attrs);
        createAndSetTabIntent();
    }

    public LinkHeaderBody(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createAndSetTabIntent();
    }

    private void createAndSetTabIntent() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_close_black_24dp);
        builder.setCloseButtonIcon(bitmap);
        this.chromeTabIntent = builder.build();
    }

    /**
     * Display classes:
     *
     * 1- Images: post_hint=link or image
     *    - GIF: https://www.reddit.com/r/instant_regret/comments/6bapsv/dancing_in_the_street/.json
     *    - /r/Images/comments/6b4t1j/a_cipher/"
     * 2- Videos: post_hint="rich:video", media.type="youtube.com" url=youtubeurl
     *    - https://www.reddit.com/r/Android/comments/6ba1ii/htc_u11_first_look/.json
     * 3- No images / pure text: has selftext
     *   - https://www.reddit.com/r/Android/comments/6b5dof/psa_for_those_installing_magisk_today_over_supersu/.json
     * 4- Pure link / Url preview: selftext is empty
     *   - https://www.reddit.com/r/Android/comments/6bdm5q/iphone_user_asking_android_users_what_do_you/.json
     *   - https://www.reddit.com/r/Android/comments/6bbqnk/meizu_brings_its_midrange_m5_smartphone_to_india/.json
     *   Both selftext and preview:
     *   https://www.reddit.com/r/Android/comments/6bb6g5/dev_new_join_release_remote_settings_chrome/.json
     */
    public void setData(Post model) {
        this.removeAllViews();
        String hint = model.postHint == null? "" : model.postHint;
        View contentView = null;
        switch (hint.toLowerCase()) {
            case "link":
                Link linkContent = new Link(getContext());
                linkContent.setData(model);
                contentView = linkContent;
                this.chromeTabUrl = model.url;
                contentView.setOnClickListener(openCustomChromeTab);
                break;
            case "rich:video":
            case "image":
                Image imageContent = new Image(getContext());
                imageContent.setData(model);
                contentView = imageContent;
                this.chromeTabUrl = model.url;
                contentView.setOnClickListener(openCustomChromeTab);
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

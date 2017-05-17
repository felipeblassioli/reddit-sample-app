package org.samples.blassioli.reddit.features.posts.widgets.content;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.base.Optional;

import org.samples.blassioli.reddit.api.posts.PreviewDataImage;
import org.samples.blassioli.reddit.features.posts.model.Post;

import java.util.List;


public class Self extends Link {
    private static final int MAX_TEXT_LENGTH = 256;

    public Self(Context context) {
        super(context);
    }

    public Self(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Self(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setData(Post model) {
        super.setData(model);
        if(TextUtils.isEmpty(model.thumbnail) || model.thumbnail.equals("self")){
            String previewImageUrl = model.getFirstPreviewImageOrNull();
            if(previewImageUrl != null) {
                setThumbnail(previewImageUrl);
            }
        }
        if(model.selftext != null) {
            this.subtitle.setVisibility(View.VISIBLE);
            this.subtitle.setText(getSelfText(model));
        }
    }

    private String getSelfText(Post model) {
        String rawSelfTest = model.selftext;
        if(rawSelfTest.length() > MAX_TEXT_LENGTH) {
            return rawSelfTest.substring(0, MAX_TEXT_LENGTH) + " ...";
        } else {
            return rawSelfTest;
        }
    }
}

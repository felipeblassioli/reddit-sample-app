package org.samples.blassioli.reddit.features.posts.model;

import com.google.common.base.Optional;

import org.parceler.Parcel;
import org.samples.blassioli.reddit.api.posts.PreviewData;
import org.samples.blassioli.reddit.api.posts.PreviewDataImage;

import java.util.List;

@Parcel
public class Post {
    public String id;
    public String author;
    public String title;
    public String numComments;
    public Long created;
    public Long createdUtc;
    public String thumbnail;
    public String url;
    public String permalink;
    public String selftext;
    public String ups;
    public PreviewData preview;
    public String domain;
    public String postHint;

    public String getFirstPreviewImageOrNull() {
        List<PreviewDataImage> images =
                Optional.fromNullable(this.preview)
                        .transform(preview -> preview.images)
                        .orNull();
        if(images != null && images.size() > 0) {
            String previewImageUrl =
                    Optional.fromNullable(images.get(0))
                            .transform(img -> img.source)
                            .transform(source -> source.url)
                            .orNull();
            return previewImageUrl;
        } else {
            return null;
        }
    }
}

package org.samples.blassioli.reddit.features.posts.model;

import org.parceler.Parcel;
import org.samples.blassioli.reddit.api.posts.PreviewData;

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
}

package org.samples.blassioli.reddit.features.posts;

public class Post {
    String id;
    String author;
    String title;
    String numComments;
    Long created;
    Long createdUtc;
    String thumbnail;
    String url;
    String permalink;
    String selftext;
    String ups;

    public Post(String id, String author, String title, String numComments,
                Long created, Long createdUtc, String thumbnail, String url, String permalink,
                String selftext, String ups) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.numComments = numComments;
        this.created = created;
        this.createdUtc = createdUtc;
        this.thumbnail = thumbnail;
        this.url = url;
        this.permalink = permalink;
        this.selftext = selftext;
        this.ups = ups;
    }
}

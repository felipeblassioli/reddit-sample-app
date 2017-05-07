package org.samples.blassioli.reddit.features.posts;

public class Post {
    String author;
    String title;
    String numComments;
    String created;
    String thumbnail;
    String url;

    public Post(String author, String title, String numComments, String created, String thumbnail, String url) {
        this.author = author;
        this.title = title;
        this.numComments = numComments;
        this.created = created;
        this.thumbnail = thumbnail;
        this.url = url;
    }
}

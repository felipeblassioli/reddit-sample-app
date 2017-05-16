package org.samples.blassioli.reddit.api.posts;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import org.samples.blassioli.reddit.features.posts.Post;
import org.samples.blassioli.reddit.features.posts.PostListModel;

import java.util.ArrayList;

public class PostsModelMapper {

    private com.google.common.base.Function<Link, Post> toPost =
            new com.google.common.base.Function<Link, Post>() {
                @Override
                public Post apply(Link c) {
                    LinkData data = c.data;
                    Post post = new Post();
                    post.id = data.id;
                    post.author = data.author;
                    post.title = data.title;
                    post.numComments = data.num_comments;
                    post.created = data.created;
                    post.createdUtc = data.created_utc;
                    post.thumbnail = data.thumbnail;
                    post.url = data.url;
                    post.permalink = data.permalink;
                    post.selftext = data.selftext;
                    post.ups = data.ups;
                    post.preview = data.preview;
                    post.domain = data.domain;
                    post.postHint = data.post_hint;
                    return post;
                }
            };

    public PostListModel from(ListingOfLinks response, String limit) {
        return Optional.fromNullable(response.data)
                .transform(data -> {
                    return new PostListModel(
                            new ArrayList<>(Lists.transform(data.children, toPost)),
                            data.after,
                            limit);
                })
                .or(PostListModel.getEmpty());
    }

}

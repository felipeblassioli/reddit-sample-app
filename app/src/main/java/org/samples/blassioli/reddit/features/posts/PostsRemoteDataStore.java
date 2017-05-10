package org.samples.blassioli.reddit.features.posts;


import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import org.samples.blassioli.reddit.api.posts.RedditApi;
import org.samples.blassioli.reddit.api.posts.RedditListingChildrenResponse;
import org.samples.blassioli.reddit.api.posts.RedditListingResponse;
import org.samples.blassioli.reddit.api.posts.RedditPostsDataResponse;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


public class PostsRemoteDataStore implements PostsDataStore {

    private final RedditApi api;

    private com.google.common.base.Function<RedditListingChildrenResponse, Post> toPost =
            new com.google.common.base.Function<RedditListingChildrenResponse, Post>() {
                @Override
                public Post apply(RedditListingChildrenResponse c) {
                    RedditPostsDataResponse data = c.data;
                    return new Post(
                            data.id,
                            data.author,
                            data.title,
                            data.num_comments,
                            data.created,
                            data.created_utc,
                            data.thumbnail,
                            data.url,
                            data.permalink,
                            data.selftext,
                            data.ups);
                }
            };

    @Inject
    public PostsRemoteDataStore(RedditApi api) {
        this.api = api;
    }

    @Override
    public Observable<PostListModel> getPostsList(String subreddit, String label, String after, String limit) {
        return api.getSubRedditPosts(subreddit, label, after, limit)
                .map(new Function<RedditListingResponse, PostListModel>() {
                    @Override
                    public PostListModel apply(@NonNull RedditListingResponse response) throws Exception {
                        return Optional.fromNullable(response.data)
                                .transform(data -> {
                                    return new PostListModel(
                                            new ArrayList<>(Lists.transform(data.children, toPost)),
                                            data.after,
                                            limit);
                                })
                                .or(PostListModel.getEmpty());
                    }
                })
                .toObservable();
    }

}

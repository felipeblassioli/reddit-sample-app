package org.samples.blassioli.reddit.features.posts.data;


import org.samples.blassioli.reddit.api.posts.PostsModelMapper;
import org.samples.blassioli.reddit.features.posts.api.RedditApi;
import org.samples.blassioli.reddit.features.posts.model.PostListModel;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PostsRemoteDataStore implements PostsDataStore {

    private final RedditApi api;

    private final PostsModelMapper postsMapper;

    @Inject
    public PostsRemoteDataStore(RedditApi api) {
        this.api = api;
        this.postsMapper = new PostsModelMapper();
    }

    @Override
    public Observable<PostListModel> getPostsList(String subreddit, String label, String after, String limit) {
        return api.getSubRedditPosts(subreddit, label, after, limit)
                .map(response -> postsMapper.from(response, limit))
                .toObservable();
    }

}

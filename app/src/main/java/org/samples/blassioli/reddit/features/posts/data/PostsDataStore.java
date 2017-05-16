package org.samples.blassioli.reddit.features.posts.data;

import org.samples.blassioli.reddit.features.posts.model.PostListModel;

import io.reactivex.Observable;

public interface PostsDataStore {
    Observable<PostListModel> getPostsList(String subreddit, String label, String from, String limit);
}

package org.samples.blassioli.reddit.features.posts;

import io.reactivex.Observable;

public interface PostsDataStore {
    Observable<PostListModel> getPostsList(String subreddit, String label, String from, String limit);
}

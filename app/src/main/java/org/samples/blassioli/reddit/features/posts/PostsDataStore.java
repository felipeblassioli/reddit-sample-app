package org.samples.blassioli.reddit.features.posts;

import java.util.List;

import io.reactivex.Observable;

public interface PostsDataStore {
    Observable<List<Post>> getPostsList(String subreddit, String label, String from, String limit);
}

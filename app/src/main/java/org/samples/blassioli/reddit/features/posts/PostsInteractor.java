package org.samples.blassioli.reddit.features.posts;

import org.samples.blassioli.reddit.BaseRxInteractor;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;

import java.util.List;

import io.reactivex.Observable;

public class PostsInteractor extends BaseRxInteractor<List<Post>, Void> {

    private final PostsDataStore dataStore;

    public PostsInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PostsDataStore dataStore) {
        super(threadExecutor, postExecutionThread);
        this.dataStore = dataStore;
    }

    protected Observable<List<Post>> buildObservable(Void unused) {
        return dataStore.getPostsList("Android", "new", "", "10");
    }

}

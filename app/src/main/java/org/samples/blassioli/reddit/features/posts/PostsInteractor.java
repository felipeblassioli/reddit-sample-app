package org.samples.blassioli.reddit.features.posts;

import org.samples.blassioli.reddit.BaseRxInteractor;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PostsInteractor extends BaseRxInteractor<PostListModel, PostsInteractor.Params> {

    private final PostsDataStore dataStore;

    @Inject
    public PostsInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PostsDataStore dataStore) {
        super(threadExecutor, postExecutionThread);
        this.dataStore = dataStore;
    }

    protected Observable<PostListModel> buildObservable(Params params) {
        return dataStore.getPostsList(params.subreddit, params.label, params.after, params.limit)
                .timeout(5500, TimeUnit.MILLISECONDS);
    }

    public static class Params {
        public final String subreddit;
        public final String label;
        public final String after;
        public final String limit;

        public Params(String subreddit, String label, String after, String limit) {
            this.subreddit = subreddit;
            this.label = label;
            this.after = after;
            this.limit = limit;
        }
    }

}

package org.samples.blassioli.reddit.features.posts;

import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.features.posts.PostsDataStore;
import org.samples.blassioli.reddit.features.posts.PostsInteractor;
import org.samples.blassioli.reddit.features.posts.PostsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsModule {

    @Provides
    PostsInteractor providesPostsInteractor(ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread,
                                            PostsDataStore postsDataStore) {
        return new PostsInteractor(threadExecutor, postExecutionThread, postsDataStore);
    }

    @Provides
    PostsPresenter providesPostsPresenter(PostsInteractor interactor) {
        return new PostsPresenter(interactor);
    }
}

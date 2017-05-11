package org.samples.blassioli.reddit.features.posts;

import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;

@Module
public class PostsModule {

    @Provides
    PostsInteractor providesPostsInteractor(ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread,
                                            PostsDataStore postsDataStore) {
        return new PostsInteractor(Schedulers.from(threadExecutor), postExecutionThread.getScheduler(), postsDataStore);
    }

    @Provides
    PostsPresenter providesPostsPresenter(PostsInteractor interactor) {
        return new PostsPresenter(interactor);
    }

}

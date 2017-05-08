package org.samples.blassioli.reddit;

import android.content.Context;

import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.features.posts.PostsDataStore;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    //void inject(BaseActivity baseActivity);

    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    PostsDataStore postsDataStore();
}
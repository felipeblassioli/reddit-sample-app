package org.samples.blassioli.reddit;

import android.content.Context;

import org.samples.blassioli.reddit.api.RedditApi;
import org.samples.blassioli.reddit.executor.JobExecutor;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.executor.UIThread;
import org.samples.blassioli.reddit.features.posts.PostsDataStore;
import org.samples.blassioli.reddit.features.posts.PostsRemoteDataStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    RedditApi providesRedditApi() {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://www.reddit.com")
                        .addConverterFactory(MoshiConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        return retrofit.create(RedditApi.class);
    }

    @Provides
    PostsDataStore providesPostsDataStore(RedditApi api) {
        return new PostsRemoteDataStore(api);
    }
}

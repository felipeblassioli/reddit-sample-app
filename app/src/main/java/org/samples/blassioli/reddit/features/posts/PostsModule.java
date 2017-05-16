package org.samples.blassioli.reddit.features.posts;

import com.squareup.moshi.Moshi;

import org.samples.blassioli.reddit.api.posts.PreviewDataConverter;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.features.posts.api.RedditApi;
import org.samples.blassioli.reddit.features.posts.data.PostsDataStore;
import org.samples.blassioli.reddit.features.posts.data.PostsRemoteDataStore;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class PostsModule {

    @Provides
    PostsInteractor providesPostsInteractor(ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread,
                                            PostsDataStore postsDataStore) {
        return new PostsInteractor(Schedulers.from(threadExecutor), postExecutionThread.getScheduler(), postsDataStore);
    }


    @Provides
    RedditApi providesRedditApi() {
        Moshi moshi = new Moshi.Builder()
                .add(new PreviewDataConverter())
                .build();
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://www.reddit.com")
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        return retrofit.create(RedditApi.class);
    }

    @Provides
    PostsDataStore providesPostsDataStore(RedditApi api) {
        return new PostsRemoteDataStore(api);
    }

    @Provides
    PostsPresenter providesPostsPresenter(PostsInteractor interactor) {
        return new PostsPresenter(interactor);
    }

}

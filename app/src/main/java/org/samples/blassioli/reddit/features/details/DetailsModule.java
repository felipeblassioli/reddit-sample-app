package org.samples.blassioli.reddit.features.details;

import com.squareup.moshi.Moshi;

import org.samples.blassioli.reddit.api.posts.PreviewDataConverter;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.features.details.api.DetailsApi;
import org.samples.blassioli.reddit.features.details.api.DetailsApiResponseConverter;
import org.samples.blassioli.reddit.features.details.data.DetailsDataStore;
import org.samples.blassioli.reddit.features.details.data.DetailsRemoteDataStore;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class DetailsModule {

    @Provides
    DetailsApi providesDetailsApi() {
        Moshi moshi = new Moshi.Builder()
                .add(new DetailsApiResponseConverter())
                .add(new PreviewDataConverter())
                .build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://www.reddit.com")
                        .client(client)
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        return retrofit.create(DetailsApi.class);
    }

    @Provides
    DetailsDataStore providesDetailsDataStore(DetailsApi api) {
        return new DetailsRemoteDataStore(api);
    }

    @Provides
    DetailsInteractor providesDetailsInteractor(ThreadExecutor threadExecutor,
                                                PostExecutionThread postExecutionThread,
                                                DetailsDataStore detailsDataStore) {
        return new DetailsInteractor(Schedulers.from(threadExecutor), postExecutionThread.getScheduler(), detailsDataStore);
    }

    @Provides
    DetailsPresenter providesDetailsPresenter(DetailsInteractor interactor) {
        return new DetailsPresenter(interactor);
    }

}

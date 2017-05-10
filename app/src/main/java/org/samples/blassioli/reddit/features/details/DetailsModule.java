package org.samples.blassioli.reddit.features.details;

import com.squareup.moshi.Moshi;

import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.features.details.api.DetailsApi;
import org.samples.blassioli.reddit.features.details.api.DetailsApiResponseConverter;
import org.samples.blassioli.reddit.features.details.data.DetailsDataStore;
import org.samples.blassioli.reddit.features.details.data.DetailsRemoteDataStore;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class DetailsModule {

    @Provides
    DetailsApi providesDetailsApi() {
        Moshi moshi = new Moshi.Builder()
                .add(new DetailsApiResponseConverter())
                .build();
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://www.reddit.com")
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
        return new DetailsInteractor(threadExecutor, postExecutionThread, detailsDataStore);
    }

    @Provides
    DetailsPresenter providesDetailsPresenter(DetailsInteractor interactor) {
        return new DetailsPresenter(interactor);
    }

}

package org.samples.blassioli.reddit.features.details;


import org.samples.blassioli.reddit.BaseRxInteractor;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.features.details.data.DetailsDataStore;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DetailsInteractor extends BaseRxInteractor<DetailsModel, DetailsInteractor.Params> {

    private final DetailsDataStore dataStore;

    @Inject
    public DetailsInteractor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, DetailsDataStore dataStore) {
        super(threadExecutor, postExecutionThread);
        this.dataStore = dataStore;
    }

    @Override
    protected Observable<DetailsModel> buildObservable(Params params) {
        return dataStore.getDetails(params.subreddit, params.id)
                .timeout(5500, TimeUnit.MILLISECONDS);
    }

    public static class Params {
        public final String subreddit;
        public final String id;

        public Params(String subreddit, String id) {
            this.subreddit = subreddit;
            this.id = id;
        }
    }

}

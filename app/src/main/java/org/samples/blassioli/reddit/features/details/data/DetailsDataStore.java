package org.samples.blassioli.reddit.features.details.data;

import org.samples.blassioli.reddit.features.details.model.DetailsModel;

import io.reactivex.Observable;

public interface DetailsDataStore {
    Observable<DetailsModel> getDetails(String subreddit, String id);
}

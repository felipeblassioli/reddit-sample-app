package org.samples.blassioli.reddit.features.details.data;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import org.samples.blassioli.reddit.features.details.api.DataChildrenItem;
import org.samples.blassioli.reddit.features.details.api.DataChildrenItemContent;
import org.samples.blassioli.reddit.features.details.api.DetailsApi;
import org.samples.blassioli.reddit.features.details.api.DetailsApiResponse;
import org.samples.blassioli.reddit.features.details.model.DetailsItem;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class DetailsRemoteDataStore implements DetailsDataStore {
    private final DetailsApi api;

    @Inject
    public DetailsRemoteDataStore(DetailsApi api) {
        this.api = api;
    }

    private DetailsItem toDetailsItem(DataChildrenItem itemJson) {
        checkNotNull(itemJson.kind);
        checkArgument(itemJson.kind.equals("t1"));

        DataChildrenItemContent data = itemJson.data;

        DetailsItem response = new DetailsItem();
        response.id = data.id;
        response.author = data.author;
        response.body = data.body;
        response.ups = data.ups;
        response.created = data.created;
        response.createdUtc = data.created_utc;
        return response;
    }

    private DetailsModel toDetailsModel(DetailsApiResponse response) {
        List<DetailsItem> items =
                Optional.fromNullable(response)
                        .transform(r -> r.childWithT1)
                        .transform(child -> child.data)
                        .transform(data -> data.children)
                        .transform(children -> Lists.transform(children, i -> toDetailsItem(i)))
                        .or(new ArrayList<>());
        return new DetailsModel(
                new ArrayList<>(items),
                ""
        );
    }

    @Override
    public Observable<DetailsModel> getDetails(String subreddit, String id) {
        final int repliesDepth = 3;
        return api.getDetails(subreddit, id, repliesDepth)
                .map(r -> toDetailsModel(r))
                .toObservable();
    }
}

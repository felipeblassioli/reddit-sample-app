package org.samples.blassioli.reddit.features.details;

import org.samples.blassioli.reddit.BaseRxLcePresenter;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;

import javax.inject.Inject;

public class DetailsPresenter
        extends BaseRxLcePresenter<DetailsView, DetailsModel, DetailsInteractor, DetailsInteractor.Params> {

    private final DetailsInteractor interactor;

    @Inject
    public DetailsPresenter(DetailsInteractor interactor) {
        this.interactor = interactor;
    }

    public void loadData(boolean pullToRefresh, String id) {
        final String sub = "Android";
        DetailsInteractor.Params params = new DetailsInteractor.Params(sub, id);
        super.loadData(pullToRefresh, interactor, params);
    }
}

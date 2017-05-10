package org.samples.blassioli.reddit.features.details;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.samples.blassioli.reddit.AndroidApplication;
import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.model.DetailsAdapter;
import org.samples.blassioli.reddit.features.details.model.DetailsItem;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;
import org.samples.blassioli.reddit.widgets.EndlessRecyclerViewScrollListener;
import org.samples.blassioli.reddit.widgets.RecyclerLceFragment;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;

public class DetailsFragment extends RecyclerLceFragment<DetailsModel, DetailsView, DetailsPresenter,
        DetailsItem> implements DetailsView {

    private static final String P_SUBREDDIT_ID = "parameterSubredditId";

    private DetailsComponent detailsComponent;

    private String subredditId;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(String subredditId) {
        Bundle bundle = new Bundle();
        bundle.putString(P_SUBREDDIT_ID, subredditId);
        DetailsFragment f = new DetailsFragment();
        f.setArguments(bundle);
        return f;
    }

    public void injectDependencies() {
        detailsComponent = DaggerDetailsComponent.builder()
                .applicationComponent(((AndroidApplication)getActivity().getApplication()).getApplicationComponent())
                .build();
        detailsComponent.inject(this);
    }

    @Override
    public DetailsPresenter createPresenter() {
        return detailsComponent.presenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        injectDependencies();
        subredditId = getArguments().getString(P_SUBREDDIT_ID);
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        loadData(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadData(pullToRefresh, subredditId);
    }

    @Override
    protected void loadMoreData(String after) {

    }

    @Override
    protected RecyclerViewListAdapter<DetailsItem> createAdapter() {
        return new DetailsAdapter();
    }

    @Override
    protected EndlessRecyclerViewScrollListener getScrollListener() {
        return null;
    }
}

package org.samples.blassioli.reddit.features.details;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;
import org.samples.blassioli.reddit.AndroidApplication;
import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.model.CommentModel;
import org.samples.blassioli.reddit.features.details.widgets.DetailsAdapter;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;
import org.samples.blassioli.reddit.features.details.widgets.LinkHeader;
import org.samples.blassioli.reddit.features.posts.model.Post;
import org.samples.blassioli.reddit.widgets.EndlessRecyclerViewScrollListener;
import org.samples.blassioli.reddit.widgets.RecyclerLceFragment;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends RecyclerLceFragment<DetailsModel, DetailsView, DetailsPresenter,
        CommentModel> implements DetailsView {

    protected static final String P_SUBREDDIT_ID = "parameterSubredditId";

    protected static final String P_LINK_DATA = "parameterLinKData";
    @BindView(R.id.link_header)
    LinkHeader header;
    private DetailsComponent detailsComponent;
    private String subredditId;
    private Post post;

    public DetailsFragment() {
    }

    public static DetailsFragment newInstance(String subredditId, Post post) {
        Bundle bundle = new Bundle();
        bundle.putString(P_SUBREDDIT_ID, subredditId);
        bundle.putParcelable(P_LINK_DATA, Parcels.wrap(post));
        DetailsFragment f = new DetailsFragment();
        f.setArguments(bundle);
        return f;
    }

    public void injectDependencies() {
        detailsComponent = DaggerDetailsComponent.builder()
                .applicationComponent(((AndroidApplication) getActivity().getApplication()).getApplicationComponent())
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
        Bundle arguments = getArguments();
        subredditId = arguments.getString(P_SUBREDDIT_ID);
        post = Parcels.unwrap(arguments.getParcelable(P_LINK_DATA));
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        ButterKnife.bind(this, view);
        initHeader();
        loadData(false);
    }

    private void initHeader() {
        if (post != null) {
            this.header.setVisibility(View.VISIBLE);
            this.header.setData(post);
        } else {
            this.header.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadData(pullToRefresh, subredditId);
    }

    @Override
    protected RecyclerViewListAdapter<CommentModel> createAdapter() {
        return new DetailsAdapter();
    }

    @Override
    protected EndlessRecyclerViewScrollListener getScrollListener() {
        return null;
    }
}

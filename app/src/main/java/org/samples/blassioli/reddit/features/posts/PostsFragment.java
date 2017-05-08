package org.samples.blassioli.reddit.features.posts;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.samples.blassioli.reddit.AndroidApplication;
import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.widgets.RecyclerLceFragment;
import org.samples.blassioli.reddit.widgets.RecyclerViewListAdapter;

import java.util.List;

public class PostsFragment extends RecyclerLceFragment<List<Post>, PostsView, PostsPresenter, PostsAdapter.ViewHolder> {

    private PostsComponent postsComponent;

    public PostsFragment() {
    }

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        injectDependencies();
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        loadData(false);
    }

    protected void injectDependencies() {

        postsComponent = DaggerPostsComponent.builder()
                .applicationComponent(((AndroidApplication)getActivity().getApplication()).getApplicationComponent())
                .build();
        postsComponent.inject(this);
    }

    @Override
    public PostsPresenter createPresenter() {
        return postsComponent.presenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return e.getMessage();
    }

    @Override
    protected RecyclerViewListAdapter<List<Post>, PostsAdapter.ViewHolder> createAdapter() {
        return new PostsAdapter(getContext());
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadData(pullToRefresh);
    }
}

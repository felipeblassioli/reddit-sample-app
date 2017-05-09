package org.samples.blassioli.reddit.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import org.samples.blassioli.reddit.BaseMvpLceView;
import org.samples.blassioli.reddit.PaginatedModel;
import org.samples.blassioli.reddit.R;


public abstract class RecyclerLceFragment<
        M extends PaginatedModel<RecyclerViewListItemType>,
        V extends BaseMvpLceView<M>,
        P extends MvpPresenter<V>,
        RecyclerViewListItemType>
        extends SwipeRefreshLceFragment<M, V, P> {
    protected View emptyView;
    protected RecyclerView recyclerView;
    protected RecyclerViewListAdapter<RecyclerViewListItemType> adapter;
    private M lastReceivedModel;
    private EndlessRecyclerViewScrollListener scrollListener;
    private boolean loadingMoreData = false;

    protected abstract RecyclerViewListAdapter<RecyclerViewListItemType> createAdapter();

    private LinearLayoutManager layoutManager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyView = view.findViewById(R.id.emptyView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = createAdapter();
        layoutManager = new LinearLayoutManager(getActivity());
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                callLoadMore();
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(adapter);
    }

    protected abstract void loadMoreData(String after);

    private void callLoadMore() {
        String after = "";
        if(lastReceivedModel != null) {
            after = lastReceivedModel.after;
        }

        // addLoaderRow
        if(adapter.peekLast() != null) {
            adapter.add(null);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        }
        loadingMoreData = true;
        loadMoreData(after);
    }

    @Override
    public void setData(M model) {
        lastReceivedModel = model;
        adapter.getItems().clear();
        adapter.setItems(model.getData());
        adapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    @Override
    public void extendData(M model) {
        loadingMoreData = false;
        lastReceivedModel = model;

        // removeLoaderRow
        if(adapter.getItemCount() > 0 && adapter.peekLast() == null) {
            adapter.removeLast();
            adapter.notifyItemRemoved(adapter.getItemCount());
        }

        adapter.addAll(model.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showContent() {

        if (adapter.getItemCount() == 0) {
            //if (isRestoringViewState()) {
            //    emptyView.setVisibility(View.VISIBLE);
            //} else {
                ObjectAnimator anim = ObjectAnimator.ofFloat(emptyView, "alpha", 0f, 1f).setDuration(400);
                anim.setStartDelay(250);
                anim.addListener(new AnimatorListenerAdapter() {

                    @Override public void onAnimationStart(Animator animation) {
                        emptyView.setVisibility(View.VISIBLE);
                    }
                });
                anim.start();
            //}
        } else {
            emptyView.setVisibility(View.GONE);
        }

        super.showContent();
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        if(loadingMoreData) {
            return;
        }
        super.showLoading(pullToRefresh);
        if (!pullToRefresh) {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        if (!pullToRefresh) {
            emptyView.setVisibility(View.GONE);
        }
    }
}

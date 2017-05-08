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
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

import org.samples.blassioli.reddit.R;

import java.util.List;


public abstract class RecyclerLceFragment<M extends List, V extends MvpLceView<M>, P extends MvpPresenter<V>, VH extends RecyclerView.ViewHolder>
        extends MvpLceFragment<RecyclerView, M, V, P> {
    protected View emptyView;
    protected RecyclerView recyclerView;
    protected RecyclerViewListAdapter<M, VH> adapter;

    protected abstract RecyclerViewListAdapter<M, VH> createAdapter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyView = view.findViewById(R.id.emptyView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = createAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setData(M data) {
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showContent() {

        if (adapter.getItemCount() == 0) {
            //if (isRestoringViewState()) {
            //    emptyView.setVisibility(View.VISIBLE);
            //} else {
                ObjectAnimator anim = ObjectAnimator.ofFloat(emptyView, "alpha", 0f, 1f).setDuration(300);
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

package org.samples.blassioli.reddit.features.posts;


import org.samples.blassioli.reddit.BaseRxLcePresenter;

import java.util.List;

import javax.inject.Inject;

public class PostsPresenter extends BaseRxLcePresenter<PostsView, List<Post>, PostsInteractor, Void> {
    private final PostsInteractor interactor;

    @Inject
    public PostsPresenter(PostsInteractor interactor) {
        this.interactor = interactor;
    }

    public void loadData(boolean pullToRefresh) {
        super.loadData(pullToRefresh, interactor, null);
    }
}

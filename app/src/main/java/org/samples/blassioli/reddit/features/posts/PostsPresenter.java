package org.samples.blassioli.reddit.features.posts;


import org.samples.blassioli.reddit.BaseRxLcePresenter;

import javax.inject.Inject;

public class PostsPresenter
        extends BaseRxLcePresenter<PostsView, PostListModel, PostsInteractor, PostsInteractor.Params> {
    private final PostsInteractor interactor;

    @Inject
    public PostsPresenter(PostsInteractor interactor) {
        this.interactor = interactor;
    }

    public void loadData(boolean pullToRefresh, String after, String limit) {
        PostsInteractor.Params params = new PostsInteractor.Params("Android", "new", after, limit);
        super.loadData(pullToRefresh, interactor, params);
    }
}

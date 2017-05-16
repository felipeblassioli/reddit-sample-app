package org.samples.blassioli.reddit.features.posts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.features.posts.data.PostsDataStore;
import org.samples.blassioli.reddit.features.posts.model.PostListModel;
import org.samples.blassioli.reddit.utils.RandomData;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PostsInteractorTest {
    @Mock
    Scheduler mockSubscribeOnScheduler;
    @Mock
    Scheduler mockObserveOnScheduler;
    @Mock
    PostsDataStore mockPostsDataStore;
    @Mock
    PostListModel mockPostsModel;
    private PostsInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new PostsInteractor(mockSubscribeOnScheduler, mockObserveOnScheduler, mockPostsDataStore);
    }

    private PostsInteractor.Params getRandomParams() {
        return new PostsInteractor.Params("Android",
                "new",
                RandomData.randomString(36),
                String.valueOf(RandomData.randomInt(0, 50)));
    }

    @Test
    public void testBuildObservable_shouldCallDataStoreAtMostOnce() {
        interactor.buildObservable(getRandomParams());
        verify(mockPostsDataStore).getPostsList(any(), any(), any(), any());
    }

    @Test
    public void testBuildObservable_shouldCallDataStore_getDetails_withSameParameters() {
        PostsInteractor.Params param = getRandomParams();
        interactor.buildObservable(param);
        verify(mockPostsDataStore).getPostsList(eq(param.subreddit), eq(param.label), eq(param.after), eq(param.limit));
    }

    @Test
    public void testBuildObservable_shouldNotInteractWithSchedulers_beforeExecuteCalled() {
        interactor.buildObservable(getRandomParams());
        verifyZeroInteractions(mockSubscribeOnScheduler);
        verifyZeroInteractions(mockObserveOnScheduler);
    }

    @Test
    public void testBuildObservable_shouldPropagateDataStoreErrors() {
        when(mockPostsDataStore.getPostsList(any(), any(), any(), any()))
                .thenReturn(Observable.error(new Exception("Observable error")));
        interactor.buildObservable(getRandomParams())
                .test()
                .assertError(e -> e.getMessage().equals("Observable error"));
    }
}

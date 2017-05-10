package org.samples.blassioli.reddit.features.posts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PostsInteractorTest {
    @Mock
    ThreadExecutor mockThreadExecutor;
    @Mock
    PostExecutionThread mockPostExecutionThread;
    @Mock
    PostsDataStore mockPostsDataStore;
    @Mock
    PostListModel mockPostsModel;
    private PostsInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new PostsInteractor(mockThreadExecutor, mockPostExecutionThread, mockPostsDataStore);
    }

    private PostsInteractor.Params getParams() {
        return new PostsInteractor.Params("Android", "new", "", "");
    }

    @Test
    public void testGetPostsListsObservableHappyCase() {
        when(mockPostsDataStore.getPostsList(any(), any(), any(), any()))
                .thenReturn(Observable.just(mockPostsModel));

        Observable<PostListModel> result = interactor.buildObservable(getParams());

        verify(mockPostsDataStore).getPostsList(any(), any(), any(), any());
        verifyNoMoreInteractions(mockPostsDataStore);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);

        result.test()
                .assertNoErrors()
                .assertValue(mockPostsModel);
    }

    @Test
    public void testGetPostsListsObservableSadCase() {
        when(mockPostsDataStore.getPostsList(any(), any(), any(), any()))
                .thenReturn(Observable.error(new Exception("Observable error")));

        Observable<PostListModel> result = interactor.buildObservable(new PostsInteractor.Params(any(), any(), any(), any()));

        verify(mockPostsDataStore).getPostsList(any(), any(), any(), any());
        verifyNoMoreInteractions(mockPostsDataStore);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);

        result.test().assertError(e -> e.getMessage().equals("Observable error"));
    }

}

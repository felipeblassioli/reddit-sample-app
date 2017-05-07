package org.samples.blassioli.reddit.features.posts;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PostsInteractorTest {
    private PostsInteractor interactor;

    @Mock
    ThreadExecutor mockThreadExecutor;

    @Mock
    PostExecutionThread mockPostExecutionThread;

    @Mock
    PostsDataStore mockPostsDataStore;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new PostsInteractor(mockThreadExecutor, mockPostExecutionThread, mockPostsDataStore);
    }

    @Test
    public void testGetPostsListsObservableHappyCase() {
        interactor.buildObservable(null);

        verify(mockPostsDataStore).getPostsList(any(), any(), any(), any());
        verifyNoMoreInteractions(mockPostsDataStore);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }

    @Test
    public void testGetPostsListsObservableSadCase() {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Observable error");

        when(mockPostsDataStore.getPostsList(any(), any(), any(), any()))
                .thenReturn(Observable.error(new Exception("Observable error")));

        Observable<List<Post>> result = interactor.buildObservable(null);

        verify(mockPostsDataStore).getPostsList(any(), any(), any(), any());
        verifyNoMoreInteractions(mockPostsDataStore);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);

        result.blockingFirst();
    }

}

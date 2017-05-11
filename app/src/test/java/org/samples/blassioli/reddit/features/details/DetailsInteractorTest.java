package org.samples.blassioli.reddit.features.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.features.details.data.DetailsDataStore;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;
import org.samples.blassioli.reddit.utils.RandomData;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DetailsInteractorTest {

    @Mock
    Scheduler mockSubscribeOnScheduler;

    @Mock
    Scheduler mockObserveOnScheduler;

    @Mock
    DetailsDataStore mockDetailsDataStore;

    @Mock
    DetailsModel mockDetailsModel;

    private DetailsInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new DetailsInteractor(mockSubscribeOnScheduler, mockObserveOnScheduler, mockDetailsDataStore);
    }

    private DetailsInteractor.Params getRandomParams() {
        return new DetailsInteractor.Params("Android", RandomData.randomString(64));
    }

    @Test
    public void testBuildObservable_shouldCallDataStoreAtMostOnce() {
        interactor.buildObservable(getRandomParams());
        verify(mockDetailsDataStore).getDetails(any(), any());
    }

    @Test
    public void testBuildObservable_shouldCallDataStore_getDetails_withSameParameters() {
        DetailsInteractor.Params param = getRandomParams();
        interactor.buildObservable(param);
        verify(mockDetailsDataStore).getDetails(eq(param.subreddit), eq(param.id));
    }

    @Test
    public void testBuildObservable_shouldNotInteractWithSchedulers_beforeExecuteCalled() {
        interactor.buildObservable(getRandomParams());
        verifyZeroInteractions(mockSubscribeOnScheduler);
        verifyZeroInteractions(mockObserveOnScheduler);
    }

    @Test
    public void testBuildObservable_shouldPropagateDataStoreErrors() {
        when(mockDetailsDataStore.getDetails(any(), any()))
                .thenReturn(Observable.error(new Exception("Observable error")));
        interactor.buildObservable(getRandomParams())
                .test()
                .assertError(e -> e.getMessage().equals("Observable error"));
    }
}

package org.samples.blassioli.reddit.features.details;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;
import org.samples.blassioli.reddit.features.details.data.DetailsDataStore;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;

import io.reactivex.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DetailsInteractorTest {

    @Mock
    ThreadExecutor mockThreadExecutor;

    @Mock
    PostExecutionThread mockPostExecutionThread;

    @Mock
    DetailsDataStore mockDetailsDataStore;

    @Mock
    DetailsModel mockDetailsModel;

    private DetailsInteractor interactor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new DetailsInteractor(mockThreadExecutor, mockPostExecutionThread, mockDetailsDataStore);
    }

    private DetailsInteractor.Params getParams() {
        return new DetailsInteractor.Params("Android", "linkId");
    }

    @Test
    public void testGetDetailsHappyCase() {
        when(mockDetailsDataStore.getDetails(any(), any()))
                .thenReturn(Observable.just(mockDetailsModel));

        Observable<DetailsModel> result = interactor.buildObservable(getParams());

        verify(mockDetailsDataStore).getDetails(any(), any());
        verifyNoMoreInteractions(mockDetailsDataStore);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);

        result.test().assertNoErrors()
                .assertValue(mockDetailsModel);
    }

    @Test
    public void testGetDetailsSadCase() {
        when(mockDetailsDataStore.getDetails(any(), any()))
                .thenReturn(Observable.error(new Exception("Observable error")));

        Observable<DetailsModel> result =
                interactor.buildObservable(getParams());

        verify(mockDetailsDataStore).getDetails(any(), any());
        verifyNoMoreInteractions(mockDetailsDataStore);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);

        result.test().assertError(e -> e.getMessage().equals("Observable error"));
    }
}

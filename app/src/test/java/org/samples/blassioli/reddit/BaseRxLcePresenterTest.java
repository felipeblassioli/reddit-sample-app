package org.samples.blassioli.reddit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class BaseRxLcePresenterTest {

    @Mock
    BaseMvpLceView<Object> mockView;
    
    @Mock
    BaseRxInteractor<Object, Void> mockInteractor;

    private TestLcePresenterClass presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new TestLcePresenterClass();
        presenter.attachView(mockView);
    }

    @Test
    public void testFirstTimeLoadData() {
        boolean pullToRefresh = false;
        presenter.loadData(pullToRefresh, mockInteractor, null);
        verify(mockView).showLoading(pullToRefresh);
        verify(mockInteractor).execute(any(), any());
        assertThat(presenter.observer, is(notNullValue()));
    }

    @Test
    public void testNoDataLoadedThenDettachView() {
        presenter.detachView(false);
        assertThat(presenter.observer, is(nullValue()));
    }

    @Test
    public void testDataLoadedThenDettachView() {
        presenter.loadData(false, mockInteractor, null);
        presenter.detachView(false);
        assertThat(presenter.observer, is(nullValue()));
    }

    private static final class TestLcePresenterClass extends BaseRxLcePresenter<BaseMvpLceView<Object>, Object, BaseRxInteractor<Object, Void>, Void> {

    }
}

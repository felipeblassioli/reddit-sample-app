package org.samples.blassioli.reddit;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

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
import static org.mockito.Mockito.when;

public class BaseRxLcePresenterTest {

    private TestLcePresenterClass presenter;

    @Mock
    MvpLceView<Object> mockView;

    @Mock
    BaseRxInteractor<Object, Void> mockInteractor;

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

    private static final class TestLcePresenterClass extends BaseRxLcePresenter<MvpLceView<Object>, Object, BaseRxInteractor<Object, Void>, Void> {

    }
}

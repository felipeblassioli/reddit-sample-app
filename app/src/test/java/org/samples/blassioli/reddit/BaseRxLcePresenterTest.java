package org.samples.blassioli.reddit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.fake.TestInteractor;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BaseRxLcePresenterTest {

    @Mock
    BaseMvpLceView<Object> mockView;

    TestInteractor testInteractor;

    TestScheduler testScheduler;

    private TestLcePresenterClass presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        testInteractor = new TestInteractor(testScheduler);
        presenter = new TestLcePresenterClass();
        presenter.attachView(mockView);
    }

    @Test
    public void testLoadData_shouldExecuteInteractor() {
        testInteractor = spy(testInteractor);
        TestInteractor.Params expectedParam = TestInteractor.Params.EMPTY;

        presenter.loadData(false, testInteractor, expectedParam);
        verify(testInteractor).execute(any(), any());
        verify(testInteractor).buildObservable(eq(expectedParam));
        assertThat(presenter.observer).isNotNull();
    }

    @Test
    public void testLoadData_shouldCreateNonNullObserver() {
        presenter.loadData(false, testInteractor, TestInteractor.Params.EMPTY);
        assertThat(presenter.observer).isNotNull();

        presenter.loadData(true, testInteractor, TestInteractor.Params.EMPTY);
        assertThat(presenter.observer).isNotNull();
    }

    @Test
    public void testLoadData_shouldCallView_showLoading_extendData_showContent_sequentially() {
        boolean pullToRefresh = false;
        Object expectedObject = new Object();

        presenter.loadData(pullToRefresh, testInteractor, new TestInteractor.Params(3, TimeUnit.SECONDS, expectedObject));
        verify(mockView, never()).extendData(expectedObject);
        verify(mockView, never()).showContent();
        verify(mockView).showLoading(pullToRefresh);

        testScheduler.advanceTimeTo(3, TimeUnit.SECONDS);
        verify(mockView).extendData(expectedObject);
        verify(mockView).showContent();
    }

    @Test
    public void testLoadData_shouldCallView_showLoading_setData_showContent_sequentially() {
        boolean pullToRefresh = true;
        Object expectedObject = new Object();

        presenter.loadData(pullToRefresh, testInteractor, new TestInteractor.Params(3, TimeUnit.SECONDS, expectedObject));
        verify(mockView, never()).extendData(expectedObject);
        verify(mockView, never()).showContent();
        verify(mockView).showLoading(pullToRefresh);

        testScheduler.advanceTimeTo(3, TimeUnit.SECONDS);
        verify(mockView).setData(expectedObject);
        verify(mockView).showContent();
    }

    @Test
    public void testLoadData_shouldCallView_onError_withTheObservableException_ifAny() {
        Exception expectedException = new Exception("Request timeout!");
        TestInteractor.Params param = new TestInteractor.Params(3, TimeUnit.SECONDS);
        param.error = expectedException;

        presenter.loadData(false, testInteractor, param);
        testInteractor.scheduler.advanceTimeBy(3, TimeUnit.SECONDS);

        verify(presenter.observerSpy).onError(expectedException);
        verify(presenter.observerSpy, never()).onNext(any());
        verify(presenter.observerSpy, never()).onComplete();
    }

    @Test
    public void testLoadData_shouldCancelPreviousLoadDataCall() {
        boolean pullToRefresh = false;
        Object firstCallExpectedObject = new Object();
        Object secondCallExpectedObject = new Object();


        // Load data by the first time
        presenter.loadData(pullToRefresh, testInteractor, new TestInteractor.Params(7, TimeUnit.SECONDS, firstCallExpectedObject));

        // Data is not loaded yet (onNext not called)
        testScheduler.advanceTimeTo(3, TimeUnit.SECONDS);
        assertThat(presenter.observer.isDisposed()).isFalse();

        // A new loadData call occurred before previous.onNext was called
        DefaultObserver<Object> previousObserver = presenter.observer;
        presenter.loadData(pullToRefresh, testInteractor, new TestInteractor.Params(7, TimeUnit.SECONDS, secondCallExpectedObject));
        // Assert that previousObserver was disposed successfully
        assertThat(previousObserver.isDisposed()).isTrue();
        assertThat(presenter.observer).isNotNull();
        assertThat(previousObserver).isNotEqualTo(presenter.observer);

        // In progress
        testScheduler.advanceTimeTo(8, TimeUnit.SECONDS);
        assertThat(presenter.observer).isNotNull();
        assertThat(presenter.observer.isDisposed()).isFalse();

        // Finished 1 second ago
        testScheduler.advanceTimeTo(15, TimeUnit.SECONDS);
        assertThat(presenter.observer).isNull();
        assertThat(presenter.observerSpy.isDisposed()).isTrue();
        verify(presenter.observerSpy).onNext(eq(secondCallExpectedObject));
        verify(presenter.observerSpy).onComplete();
    }

    @Test
    public void testEmptyDettachView() {
        presenter.detachView(false);
        assertThat(presenter.observer).isNull();

        presenter.detachView(true);
        assertThat(presenter.observer).isNull();
    }

    @Test
    public void testDetachView_shouldDisposeOfUnfinishedLoadDataObserver() {
        presenter.loadData(false, testInteractor, new TestInteractor.Params(2, TimeUnit.SECONDS));

        testInteractor.scheduler.advanceTimeTo(1, TimeUnit.SECONDS);
        assertThat(presenter.observerSpy.isDisposed()).isFalse();
        assertThat(presenter.observer).isNotNull();

        presenter.detachView(false);
        assertThat(presenter.observerSpy.isDisposed()).isTrue();
        assertThat(presenter.observer).isNull();
    }

    @Test
    public void testDetachView_shouldDisposeOfFinishedLoadDataObserver() {
        presenter.loadData(false, testInteractor, new TestInteractor.Params(2, TimeUnit.SECONDS));
        assertThat(presenter.observer.isDisposed()).isFalse();

        testScheduler.advanceTimeTo(2, TimeUnit.SECONDS);

        presenter.detachView(false);
        assertThat(presenter.observerSpy.isDisposed()).isTrue();
        assertThat(presenter.observer).isNull();
    }


    private class TestLcePresenterClass extends BaseRxLcePresenter<BaseMvpLceView<Object>, Object, BaseRxInteractor<Object, TestInteractor.Params>, TestInteractor.Params> {
        DefaultObserver<Object> observerSpy;

        @Override
        public DefaultObserver<Object> createObserver(boolean p) {
            observerSpy = spy(super.createObserver(p));
            return observerSpy;
        }

    }

}

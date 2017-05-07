package org.samples.blassioli.reddit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.executor.PostExecutionThread;
import org.samples.blassioli.reddit.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

public class BaseRxInteractorTest {
    private BaseRxInteractorTestClass interactor;

    private TestDisposableObserver<Object> testObserver;

    @Mock
    ThreadExecutor mockThreadExecutor;
    @Mock
    PostExecutionThread mockPostExecutionThread;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interactor = new BaseRxInteractorTestClass(mockThreadExecutor, mockPostExecutionThread);
        testObserver = new TestDisposableObserver<>();
        when(mockPostExecutionThread.getScheduler()).thenReturn(new TestScheduler());
    }

    @Test
    public void testBuildInteractorObservableReturnCorrectResult() {
        interactor.execute(testObserver, Params.EMPTY);

        assertThat(testObserver.valuesCount, equalTo(0));
    }

    @Test
    public void testSubscriptionWhenExecutingInteractor() {
        interactor.execute(testObserver, Params.EMPTY);
        interactor.dispose();

        assertThat(testObserver.isDisposed(), is(true));
    }

    @Test
    public void testShouldFailWhenExecuteWithNullObserver() {
        expectedException.expect(NullPointerException.class);
        interactor.execute(null, Params.EMPTY);
    }

    private static class BaseRxInteractorTestClass extends BaseRxInteractor<Object, Params> {

        BaseRxInteractorTestClass(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        }

        @Override
        protected Observable<Object> buildObservable(Params params) {
            return Observable.empty();
        }

        @Override
        public void execute(DisposableObserver<Object> observer, Params params) {
            super.execute(observer, params);
        }
    }

    private static class TestDisposableObserver<T> extends DisposableObserver<T> {
        private int valuesCount = 0;

        @Override 
        public void onNext(T value) {
            valuesCount++;
        }

        @Override 
        public void onError(Throwable e) {
        }

        @Override 
        public void onComplete() {
        }
    }

    private static class Params {
        private static Params EMPTY = new Params();
        private Params() {}
    }
}

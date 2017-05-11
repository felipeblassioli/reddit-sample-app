package org.samples.blassioli.reddit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.fake.TestInteractor;

import java.util.concurrent.TimeUnit;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BaseRxInteractorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BaseRxInteractorTestClass interactor;

    private TestDisposableObserver<Object> testObserver;

    private TestScheduler testScheduler;

    private TestObserver<Object> bla;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        interactor = spy(new BaseRxInteractorTestClass(testScheduler));
        testObserver = spy(new TestDisposableObserver<>());
    }

    @Test
    public void testExecute_shouldCallBuildObservable_withSameParameters() {
        TestInteractor.Params params = TestInteractor.Params.EMPTY;
        interactor.execute(testObserver, params);
        verify(interactor).buildObservable(params);
    }

    @Test
    public void testExecute_shouldSubscribeToBuiltObservable() {
        interactor.execute(testObserver, TestInteractor.Params.EMPTY);
        assertThat(testObserver.isSubscribed()).isTrue();
    }

    @Test
    public void testDispose_shouldDisposeOfUnfinishedObservable() {
        TestInteractor.Params params = new TestInteractor.Params(3, TimeUnit.SECONDS);
        interactor.execute(testObserver, params);
        interactor.scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

        assertThat(testObserver.isDisposed()).isFalse();
        interactor.dispose();
        assertThat(testObserver.isDisposed()).isTrue();
    }

    @Test
    public void testExecute_shouldFailWhenCalledWithNullObserver() {
        expectedException.expect(NullPointerException.class);
        interactor.execute(null, TestInteractor.Params.EMPTY);
    }

    private static class BaseRxInteractorTestClass extends TestInteractor {
        public BaseRxInteractorTestClass(TestScheduler scheduler) {
            super(scheduler);
        }
    }

    private static class TestDisposableObserver<T> extends DisposableObserver<T> {
        private int valuesCount = 0;

        private boolean subscribed = false;

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

        protected void onStart() {
            subscribed = true;
        }

        public boolean isSubscribed() {
            return this.subscribed && !isDisposed();
        }
    }

    private static class Params {
        private static Params EMPTY = new Params();

        private Params() {
        }
    }
}

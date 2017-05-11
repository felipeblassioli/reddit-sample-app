package org.samples.blassioli.reddit.fake;

import org.samples.blassioli.reddit.BaseRxInteractor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

public class TestInteractor extends BaseRxInteractor<Object, TestInteractor.Params> {
    public TestScheduler scheduler;

    public TestInteractor(TestScheduler scheduler) {
        super(scheduler, scheduler);
        this.scheduler = scheduler;
    }

    @Override
    public Observable<Object> buildObservable(TestInteractor.Params param) {
        if (param.error == null) {
            return Observable.just(param.returnValue)
                    .delay(param.delay, param.timeUnit, scheduler);
        } else {
            return Observable.error(param.error)
                    .delay(param.delay, param.timeUnit, scheduler);
        }
    }

    public static class Params {
        public static Params EMPTY = new Params();

        public final int delay;
        public final Object returnValue;
        public final TimeUnit timeUnit;
        public Exception error = null;

        public Params() {
            this(0, TimeUnit.SECONDS, new Object());
        }

        public Params(int delay, TimeUnit timeUnit) {
            this(delay, timeUnit, new Object());
        }

        public Params(int delay, TimeUnit timeUnit, Object returnValue) {
            this.delay = delay;
            this.timeUnit = timeUnit;
            this.returnValue = returnValue;
        }
    }
}


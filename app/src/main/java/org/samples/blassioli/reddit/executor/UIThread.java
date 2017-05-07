package org.samples.blassioli.reddit.executor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

//@Singleton
public class UIThread implements PostExecutionThread {

    //@Inject
    UIThread() {}

    @Override public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
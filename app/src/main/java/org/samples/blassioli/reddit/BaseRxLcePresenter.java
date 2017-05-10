package org.samples.blassioli.reddit;

import android.support.annotation.UiThread;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.observers.DisposableObserver;

public abstract class BaseRxLcePresenter<V extends BaseMvpLceView<M>, M, I extends BaseRxInteractor<M, Params>, Params> extends MvpBasePresenter<V> {

    protected DisposableObserver<M> observer;

    @Override
    public void attachView(V v) {
        super.attachView(v);
    }

    public void loadData(boolean pullToRefresh, I interactor, Params params) {
        unsubscribeIfNeeded();
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);
        }

        observer = new LceObserver(pullToRefresh);
        interactor.execute(observer, params);
    }

    @UiThread
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        unsubscribeIfNeeded();
    }

    private void unsubscribeIfNeeded() {
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
        observer = null;
    }

    private final class LceObserver extends DefaultObserver<M> {
        private final boolean pullToRefresh;

        LceObserver(boolean pullToRefresh) {
            super();
            this.pullToRefresh = pullToRefresh;
        }

        @Override
        public void onComplete() {
            super.onComplete();

            if (isViewAttached()) {
                getView().showContent();
            }
            unsubscribeIfNeeded();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            if (isViewAttached()) {
                getView().showError(e, pullToRefresh);
            }
            unsubscribeIfNeeded();
        }

        @Override
        public void onNext(M data) {
            super.onNext(data);

            if (isViewAttached()) {
                if (pullToRefresh) {
                    getView().setData(data);
                } else {
                    getView().extendData(data);
                }
            }
        }
    }
}

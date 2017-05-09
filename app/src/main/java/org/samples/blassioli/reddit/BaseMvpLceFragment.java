package org.samples.blassioli.reddit;

import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;
import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

public abstract class BaseMvpLceFragment<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
        extends MvpLceFragment<CV, M, V, P> {

    public abstract void extendData(M data);
}

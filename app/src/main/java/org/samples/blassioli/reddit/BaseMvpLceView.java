package org.samples.blassioli.reddit;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;

public interface BaseMvpLceView<M> extends MvpLceView<M> {
    void extendData(M data);
}

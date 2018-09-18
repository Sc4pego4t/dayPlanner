package ru.scapegoats.dayplanner.modules;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActivity<V> extends AppCompatActivity {
    protected V view;
    protected Presenter presenter;

    protected abstract Presenter initPresenter();
    protected abstract V initView();

    public V getView() {
        return view;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        view=initView();
        presenter=initPresenter();
        presenter.onViewAttached(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

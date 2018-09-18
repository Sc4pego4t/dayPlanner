package ru.scapegoats.dayplanner.activity.main;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import ru.scapegoats.dayplanner.R;
import ru.scapegoats.dayplanner.modules.AbstractActivity;
import ru.scapegoats.dayplanner.modules.Presenter;
import ru.scapegoats.dayplanner.utils.MySharedPreference;

public class MainActivity extends AbstractActivity<MainView> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

    @Override
    protected Presenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected MainView initView() {
        return new MainView(this,findViewById(android.R.id.content));
    }
}

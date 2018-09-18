package ru.scapegoats.dayplanner.activity.main;

import java.util.List;

import ru.scapegoats.dayplanner.activity.main.adapter.MyRecyclerViewAdapter;
import ru.scapegoats.dayplanner.modules.Presenter;
import ru.scapegoats.dayplanner.utils.MySharedPreference;

public class MainPresenter implements Presenter<MainView> {

    @Override
    public void onViewAttached(MainView view) {
        List<CardContent> list=MySharedPreference.getTodoList(view.activity);
        view.adapter=new MyRecyclerViewAdapter(list,view.activity);
        view.recyclerView.setAdapter(view.adapter);
        view.recyclerView.setLayoutManager(view.manager);


    }


    @Override
    public void onViewDetached() {

    }

    @Override
    public void onDestroyed() {

    }


}

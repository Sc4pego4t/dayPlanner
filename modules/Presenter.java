package ru.scapegoats.dayplanner.modules;

public interface Presenter<V> {
    void onViewAttached(V view);
    void onViewDetached();
    void onDestroyed();

}

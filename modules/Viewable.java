package ru.scapegoats.dayplanner.modules;

public interface Viewable<T> {
    void onAttach(T data);
}

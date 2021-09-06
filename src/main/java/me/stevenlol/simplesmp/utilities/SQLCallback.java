package me.stevenlol.simplesmp.utilities;

public interface SQLCallback<T> {

    void onQueryDone(T t);

}

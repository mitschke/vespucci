package de.tud.cs.st.vespucci.sadclient.concurrent;

import java.util.concurrent.Future;

public interface Callback<V> {

    public void set(Future<V> future);

}

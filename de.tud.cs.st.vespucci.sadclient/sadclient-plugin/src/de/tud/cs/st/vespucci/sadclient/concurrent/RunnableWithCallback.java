package de.tud.cs.st.vespucci.sadclient.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Pass the {@link Future} of a computation to a
 * {@link Callback} after it is computed.
 * 
 * @author Mateusz Parzonka
 * 
 * @param <V>
 *            The type which is passed to the {@link Callback}s set method.
 */
public final class RunnableWithCallback<V> implements Runnable {

    public RunnableWithCallback(Callable<V> callable, Callback<V> callback) {
	this.callable = callable;
	this.callback = callback;
    }

    final Callable<V> callable;
    final Callback<V> callback;

    @Override
    final public void run() {
	System.out.println("Started processing Runnable with callback " + callback);
	FutureTask<V> f = new FutureTask<V>(callable);
	f.run();
	callback.set(f);
	System.out.println("Processed Runnable with callback " + callback);
    }

}

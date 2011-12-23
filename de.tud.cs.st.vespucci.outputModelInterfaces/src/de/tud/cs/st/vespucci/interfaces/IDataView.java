package de.tud.cs.st.vespucci.interfaces;

import java.util.Iterator;

/**
 * 
 * @author Ralf Mitschke
 *
 * @param <T>
 */
public interface IDataView<T> 
{
	
	/**
	 * 
	 * @return an iterator that can be used to obtain all elements of the view at a particular point in time
	 */
	Iterator<T> iterator();
	
	/**
	 * Disposes of the given data view
	 * TODO currently not implemented
	 */
	void dispose();
	
	void register(IDataViewObserver<T> observer);
	
	void unregister(IDataViewObserver<T> observer);
}

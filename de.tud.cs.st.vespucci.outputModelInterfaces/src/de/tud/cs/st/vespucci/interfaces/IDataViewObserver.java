package de.tud.cs.st.vespucci.interfaces;

/**
 * 
 * @author Ralf Mitschke
 */
public interface IDataViewObserver<T> {

	void added(T element);

	void deleted(T element);

	void updated(T oldValue, T newValue);

}

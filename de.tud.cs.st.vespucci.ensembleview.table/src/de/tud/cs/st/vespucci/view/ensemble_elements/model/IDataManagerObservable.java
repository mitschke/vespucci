package de.tud.cs.st.vespucci.view.ensemble_elements.model;

public interface IDataManagerObservable {
	void register(IDataManagerObserver observer);
	
	void unregister(IDataManagerObserver observer);
	
	void notifyObserver();
}

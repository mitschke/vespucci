package de.tud.cs.st.vespucci.ensembleview.table.model;

public interface IDataManagerObservable {
	void register(IDataManagerObserver observer);
	
	void unregister(IDataManagerObserver observer);
	
	void notifyObserver();
}

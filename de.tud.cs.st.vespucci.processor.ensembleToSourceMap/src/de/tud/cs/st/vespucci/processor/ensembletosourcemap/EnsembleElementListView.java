package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import java.util.Iterator;
import java.util.Set;

import sae.LazyView;
import sae.Observer;
import sae.collections.Conversions;
import sae.collections.QueryResult;
import scala.collection.immutable.List;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleElementListView implements IEnsembleElementList,
		Observer<IPair<IEnsemble, ICodeElement>> {

	private LazyView<IPair<IEnsemble, ICodeElement>> view;

	private Set<IDataViewObserver<IPair<IEnsemble, ICodeElement>>> observers;

	public EnsembleElementListView(LazyView<IPair<IEnsemble, ICodeElement>> view) {
		this.view = view;
		view.addObserver(this);
		this.observers = new java.util.HashSet<IDataViewObserver<IPair<IEnsemble, ICodeElement>>>();
	}

	/**
	 * Obtain a java iterator to traverse the result set. Should not be used
	 * excessively!
	 */
	@Override
	public Iterator<IPair<IEnsemble, ICodeElement>> iterator() {

		QueryResult<IPair<IEnsemble, ICodeElement>> result = Conversions
				.lazyViewToResult(view);

		List<IPair<IEnsemble, ICodeElement>> list = result.asList();

		return scala.collection.JavaConversions.asJavaIterator(list.iterator());
	}

	@Override
	public void register(
			IDataViewObserver<IPair<IEnsemble, ICodeElement>> observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(
			IDataViewObserver<IPair<IEnsemble, ICodeElement>> observer) {
		observers.remove(observer);
	}

	@Override
	public void added(IPair<IEnsemble, ICodeElement> element) {
		System.out.println("added: " + createEnsembleText(element.getFirst()) + " :: " + element.getSecond());
		for (IDataViewObserver<IPair<IEnsemble, ICodeElement>> observer : observers) {
			observer.added(element);
		}
	}

	@Override
	public void removed(IPair<IEnsemble, ICodeElement> element) {
		System.out.println("removed: " + createEnsembleText(element.getFirst()) + " :: " + element.getSecond());
		for (IDataViewObserver<IPair<IEnsemble, ICodeElement>> observer : observers) {
			observer.deleted(element);
		}
	}

	@Override
	public void updated(IPair<IEnsemble, ICodeElement> oldElement,
			IPair<IEnsemble, ICodeElement> newElement) {
		for (IDataViewObserver<IPair<IEnsemble, ICodeElement>> observer : observers) {
			observer.updated(oldElement, newElement);
		}
	}

	@Override
	public void dispose() {
		// TODO implement disposal
	}

	
	private static String createEnsembleText(
			IEnsemble ensemble) {
		String label =  ensemble.getName();
		
		while (ensemble.getParent() != null){
			ensemble = ensemble.getParent();
			label = ensemble.getName() + "." + label;
		}
		
		return label;
	}
}

package de.tud.cs.st.vespucci.processor.unmodeledElements;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sae.LazyView;
import sae.Observer;
import sae.collections.Conversions;
import sae.collections.QueryResult;
import scala.collection.immutable.List;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IUnmodeledElementView;

public class UnmodeledElementView implements IUnmodeledElementView,
		Observer<ICodeElement> {

	private Set<IDataViewObserver<ICodeElement>> observers;

	private LazyView<ICodeElement> unmodeledElements;

	private QueryResult<ICodeElement> result;

	public UnmodeledElementView(LazyView<ICodeElement> unmodeledElements) {
		this.observers = new HashSet<IDataViewObserver<ICodeElement>>();
		this.unmodeledElements = unmodeledElements;
		this.unmodeledElements.addObserver(this);
	}

	@Override
	public Iterator<ICodeElement> iterator() {
		if (result == null) {
			result = Conversions.lazyViewToResult(unmodeledElements);
		}

		List<ICodeElement> list = result.asList();

		return scala.collection.JavaConversions.asJavaIterator(list.iterator());
	}

	@Override
	public void dispose() {
		// TODO clear the result from the list of observers
		// TODO possibly clear the whole view anyway.
	}

	@Override
	public void register(IDataViewObserver<ICodeElement> observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(IDataViewObserver<ICodeElement> observer) {
		observers.remove(observer);
	}

	@Override
	public void added(ICodeElement element) {
		for (IDataViewObserver<ICodeElement> observer : observers) {
			observer.added(element);
		}
	}

	@Override
	public void removed(ICodeElement element) {
		for (IDataViewObserver<ICodeElement> observer : observers) {
			observer.deleted(element);
		}
	}

	@Override
	public void updated(ICodeElement oldElement, ICodeElement newElement) {
		for (IDataViewObserver<ICodeElement> observer : observers) {
			observer.updated(oldElement, newElement);
		}
	}

}

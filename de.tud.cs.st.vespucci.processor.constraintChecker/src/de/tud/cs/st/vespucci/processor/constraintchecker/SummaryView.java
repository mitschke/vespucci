package de.tud.cs.st.vespucci.processor.constraintchecker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import sae.LazyView;
import sae.Observer;
import sae.collections.Conversions;
import sae.collections.QueryResult;
import scala.collection.immutable.List;
import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

public class SummaryView implements IDataView<IViolationSummary>,
		Observer<IViolationSummary> {

	private LazyView<IViolationSummary> summary;

	private Set<IDataViewObserver<IViolationSummary>> observers;

	public SummaryView(LazyView<IViolationSummary> summary) {
		this.summary = summary;
		this.summary.addObserver(this);
		this.observers = new HashSet<IDataViewObserver<IViolationSummary>>();
	}

	@Override
	public Iterator<IViolationSummary> iterator() {
		QueryResult<IViolationSummary> result = Conversions
				.lazyViewToResult(summary);

		List<IViolationSummary> list = result.asList();

		return scala.collection.JavaConversions.asJavaIterator(list.iterator());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void register(IDataViewObserver<IViolationSummary> observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(IDataViewObserver<IViolationSummary> observer) {
		observers.remove(observer);
	}

	@Override
	public void added(IViolationSummary arg0) {
		
		System.out.println("  added: " + arg0);
		
		for (IDataViewObserver<IViolationSummary> observer : observers) {
			observer.added(arg0);
		}
	}

	@Override
	public void removed(IViolationSummary arg0) {
		
		System.out.println("removed: " + arg0);
		
		for (IDataViewObserver<IViolationSummary> observer : observers) {
			observer.deleted(arg0);
		}
	}

	@Override
	public void updated(IViolationSummary arg0, IViolationSummary arg1) {
		
		System.out.println("updated: " + arg0 + " => " + arg1);
		
		for (IDataViewObserver<IViolationSummary> observer : observers) {
			observer.updated(arg0, arg1);
		}
	}

}

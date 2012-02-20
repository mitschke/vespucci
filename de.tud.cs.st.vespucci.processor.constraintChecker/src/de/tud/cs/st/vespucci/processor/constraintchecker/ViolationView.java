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
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;
import de.tud.cs.st.vespucci.interfaces.IViolationView;

/**
 * 
 * 
 * @author Ralf Mitschke
 *
 */
public class ViolationView implements IViolationView, Observer<IViolation>
{

	private LazyView<IViolation> violations;
	
	private IDataView<IViolationSummary> summaryView;
	
	private Set<IDataViewObserver<IViolation>> observers;

	
	public ViolationView(LazyView<IViolation> violations, IDataView<IViolationSummary> summaryView) {
		this.violations = violations;
		this.summaryView = summaryView;
		observers = new HashSet<IDataViewObserver<IViolation>>();
		this.violations.addObserver(this);
	}

	@Override
	public Iterator<IViolation> iterator() {
		QueryResult<IViolation> result = Conversions.lazyViewToResult(violations);

		List<IViolation> list = result.asList();

		return scala.collection.JavaConversions.asJavaIterator(list.iterator());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(IDataViewObserver<IViolation> observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(IDataViewObserver<IViolation> observer) {
		observers.remove(observer);
	}

	@Override
	public void added(IViolation arg0) {
		
		//System.out.println("  added: " + arg0);
		
		for (IDataViewObserver<IViolation> o : observers) {
			o.added(arg0);
		}
	}

	@Override
	public void removed(IViolation arg0) {
		
		//System.out.println("removed: " + arg0);
		
		for (IDataViewObserver<IViolation> o : observers) {
			o.deleted(arg0);
		}
	}

	@Override
	public void updated(IViolation arg0, IViolation arg1) {

		//System.out.println("updated: " + arg0 + " => " + arg1);
		
		for (IDataViewObserver<IViolation> o : observers) {
			o.updated(arg0, arg1);
		}
	}

	@Override
	public IDataView<IViolationSummary> getSummaryView() {
		return summaryView;
	}

}

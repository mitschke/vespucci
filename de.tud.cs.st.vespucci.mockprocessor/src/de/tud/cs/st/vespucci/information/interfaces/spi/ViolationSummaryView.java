package de.tud.cs.st.vespucci.information.interfaces.spi;

import java.util.Iterator;
import java.util.Set;

import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

public class ViolationSummaryView implements IDataView<IViolationSummary> {

	private Set<IViolationSummary> violationSummary;
	
	public ViolationSummaryView(Set<IViolationSummary> set) {
		this.violationSummary = set;
	}
	
	@Override
	public Iterator<IViolationSummary> iterator() {
		// TODO Auto-generated method stub
		return violationSummary.iterator();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(IDataViewObserver<IViolationSummary> observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregister(IDataViewObserver<IViolationSummary> observer) {
		// TODO Auto-generated method stub
		
	}

}

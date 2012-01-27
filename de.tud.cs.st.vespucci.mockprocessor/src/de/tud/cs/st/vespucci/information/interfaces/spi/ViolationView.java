package de.tud.cs.st.vespucci.information.interfaces.spi;

import java.util.Iterator;
import java.util.Set;

import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;
import de.tud.cs.st.vespucci.interfaces.IViolationView;

public class ViolationView implements IViolationView {

	private Set<IViolation> violations;
	private IDataView<IViolationSummary> dataView;
	
	public ViolationView(Set<IViolation> violations, IDataView<IViolationSummary> dataView){
		this.violations = violations;
		this.dataView = dataView;
	}
	
	@Override
	public Iterator<IViolation> iterator() {
		return violations.iterator();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void register(IDataViewObserver<IViolation> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(IDataViewObserver<IViolation> observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public IDataView<IViolationSummary> getSummaryView() {
		return dataView;
	}

}

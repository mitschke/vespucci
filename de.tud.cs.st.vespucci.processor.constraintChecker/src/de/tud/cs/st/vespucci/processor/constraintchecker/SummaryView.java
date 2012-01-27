package de.tud.cs.st.vespucci.processor.constraintchecker;

import java.util.Iterator;

import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

public class SummaryView implements IDataView<IViolationSummary>{

	@Override
	public Iterator<IViolationSummary> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<IViolationSummary>() {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public IViolationSummary next() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
			
		};
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

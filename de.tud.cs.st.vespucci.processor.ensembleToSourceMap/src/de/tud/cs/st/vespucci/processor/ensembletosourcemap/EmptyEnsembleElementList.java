package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import java.util.Iterator;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EmptyEnsembleElementList implements IEnsembleElementList {

	@Override
	public Iterator<IPair<IEnsemble, ICodeElement>> iterator() {
		return new Iterator<IPair<IEnsemble, ICodeElement>>() {

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public IPair<IEnsemble, ICodeElement> next() {
				return null;
			}

			@Override
			public void remove() {

			}
		};
	}

	@Override
	public void register(
			IDataViewObserver<IPair<IEnsemble, ICodeElement>> observer) {
		// do nothing
	}

	@Override
	public void unregister(
			IDataViewObserver<IPair<IEnsemble, ICodeElement>> observer) {
		// do nothing
	}

	@Override
	public void dispose() {
		// do nothing
	}

}

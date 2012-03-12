package de.tud.cs.st.vespucci.view.table;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;

/**
 * 
 * @author 
 *
 * @param <A>
 */
public class DataViewContentProvider<A> implements IStructuredContentProvider {

	private IDataView<A> dataView;

	@Override
	public void dispose() {
		if (dataView != null)
			dataView.dispose();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (dataView != null && dataView == oldInput) {
			dataView.dispose();
			dataView = null;
		}
		
		if ((newInput != null)&&(newInput instanceof IDataView<?>)&&(viewer instanceof TableViewer)){
			final TableViewer tableViewer = (TableViewer) viewer;
			dataView = (IDataView<A>) newInput;
			
			dataView.register(new IDataViewObserver<A>() {

				@Override
				public void updated(final A oldValue,
						final A newValue) {
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							tableViewer.remove(oldValue);
							tableViewer.add(newValue);
						}
					});
				}

				@Override
				public void deleted(final A element) {

					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							tableViewer.remove(element);
						}
					});
				}

				@Override
				public void added(final A element) {

					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							tableViewer.add(element);
						}
					});
				}
			});
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (dataView == null){
			return new Object[0];
		}
		Set<A> result = new HashSet<A>();
		for (Iterator<A> iterator = dataView.iterator(); iterator
				.hasNext();) {
			A element = (A) iterator.next();
			result.add(element);
		}
		return result.toArray();
	}
}

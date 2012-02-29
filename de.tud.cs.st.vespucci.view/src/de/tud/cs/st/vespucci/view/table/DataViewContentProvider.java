package de.tud.cs.st.vespucci.view.table;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import de.tud.cs.st.vespucci.interfaces.IDataView;

public class DataViewContentProvider<A> implements IStructuredContentProvider {

	private IDataView<A> dataView;

	@Override
	public void dispose() {
		if (dataView != null){
			dataView.dispose();
		}
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (dataView != null && dataView.equals(oldInput)) {
			dataView.dispose();
			dataView = null;
		}
		if ((newInput != null)&&(newInput instanceof IDataView<?>)&&(viewer instanceof TableViewer)){
			
			@SuppressWarnings("unchecked")
			IDataView<A> dataView = (IDataView<A>) newInput;
			final TableViewer tableViewer = (TableViewer) viewer;

			dataView.register(new DataViewObserver<A>() {

				@Override
				public void added(final A element) {
					Display.getDefault().asyncExec(new Runnable() {

						@Override
						public void run() {
							tableViewer.add(element);
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
			});
			
			
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Set<A> result = new HashSet<A>();
		for (Iterator<A> iterator = dataView.iterator(); iterator
				.hasNext();) {
			A element = (A) iterator.next();
			result.add(element);
		}
		return result.toArray();
	}

}

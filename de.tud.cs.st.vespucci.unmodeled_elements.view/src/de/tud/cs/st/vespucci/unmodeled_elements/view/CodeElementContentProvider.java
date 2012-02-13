package de.tud.cs.st.vespucci.unmodeled_elements.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;

class CodeElementContentProvider implements IStructuredContentProvider {

	private IDataView<ICodeElement> elementView;

	@Override
	public void dispose() {
		if (elementView != null)
			elementView.dispose();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (elementView != null && elementView == oldInput) {
			elementView.dispose();
			elementView = null;
		}
		if (newInput == null)
			return;

		if (!(newInput instanceof IDataView<?>))
			throw new IllegalArgumentException(newInput
					+ " should be of type IDataView<ICodeElement>");
		if (!(viewer instanceof TableViewer))
			throw new IllegalArgumentException(viewer
					+ " should be of type TableViewer");

		final TableViewer tableViewer = (TableViewer) viewer;
		elementView = (IDataView<ICodeElement>) newInput;
		elementView.register(new IDataViewObserver<ICodeElement>() {

			@Override
			public void updated(final ICodeElement oldValue,
					final ICodeElement newValue) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						tableViewer.remove(oldValue);
						tableViewer.add(newValue);
					}
				});
			}

			@Override
			public void deleted(final ICodeElement element) {

				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						tableViewer.remove(element);
					}
				});
			}

			@Override
			public void added(final ICodeElement element) {

				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						tableViewer.add(element);
					}
				});
			}
		});
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<ICodeElement> result = new ArrayList<ICodeElement>();
		for (Iterator<ICodeElement> iterator = elementView.iterator(); iterator
				.hasNext();) {
			ICodeElement element = (ICodeElement) iterator.next();
			result.add(element);
		}
		return result.toArray();
	}

}
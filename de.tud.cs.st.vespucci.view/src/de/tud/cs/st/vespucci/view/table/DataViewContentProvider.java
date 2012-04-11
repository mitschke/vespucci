/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Technology Group Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
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
 * An generic implementation of IStructuredContentProvider
 * which can be used in combination with IDataView<A> as input.
 * 
 * @see de.tud.cs.st.vespucci.interfaces.IDataView<A>
 * 
 * @author Olav Lenz
 * @author Ralf Mitschke
 * @author Patrick Gottschaemmer
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

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
package de.tud.cs.st.vespucci.marker;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.utilities.Util;

/**
 * Mark Violations in a given project.
 */
public class Marker implements IResultProcessor, IDataViewObserver<IViolation> {

	private IProject project;
	private IViolationView violationView;
	private DescriptionFactory descFab;
	
	@Override
	public void processResult(Object result, IFile file) {
		this.project = file.getProject();
		this.descFab = new DescriptionFactory();
		
		violationView = Util.adapt(result, IViolationView.class);
		
		if (violationView == null){
			return;
		}
		
		violationView.register(this);
		
		for (Iterator<IViolation> i = violationView.iterator(); i.hasNext();){
			markIViolation(i.next());
		}
		
		new ViolationSummaryMarker(violationView.getSummaryView());
		
	}
	
	@Override
	public void added(IViolation element) {
		markIViolation(element);
	}

	@Override
	public void deleted(IViolation element) {
		unmarkIViolation(element);
	}

	@Override
	public void updated(IViolation oldValue, IViolation newValue) {
		markIViolation(oldValue);
		unmarkIViolation(newValue);
	}

	private void unmarkIViolation(IViolation violation) {
		CodeElementMarker.unmarkIViolation(violation);
	}

	private void markIViolation(IViolation violation) {
		if (violation.getSourceElement() != null){
			CodeElementFinder.startSearch(violation.getSourceElement(), project, new CodeElementMarker(true, createSourceViolationDescription(violation), violation));
		}
		if (violation.getTargetElement() != null){
			CodeElementFinder.startSearch(violation.getTargetElement(), project, new CodeElementMarker(false, createTargetViolationDescription(violation), violation));
		}	
	}

	private String createSourceViolationDescription(IViolation violation) {
				
		return this.descFab.getDescription(violation);

	}

	private String createTargetViolationDescription(IViolation violation) {
		return createSourceViolationDescription(violation);
	}

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IViolationView.class.equals(resultClass);
	}

	@Override
	public void cleanUp() {
		CodeElementMarker.deleteAllMarkers();
		ViolationSummaryMarker.deleteAllMarkers();
		//violationView.unregister(this);
	}

}

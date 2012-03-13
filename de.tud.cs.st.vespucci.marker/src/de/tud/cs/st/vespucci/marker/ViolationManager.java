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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IViolation;

/**
 * Explicit implementation of DataViewObserver for IViolations.
 * 
 * @author 
 */
public class ViolationManager extends DataViewObserver<IViolation> {

	public static final int TARGET = 0;
	public static final int SOURCE = 1;

	private Map<IViolation, Set<IMarker>> marks;
	private DescriptionFactory descriptionFactory;

	public ViolationManager(){
		marks = new HashMap<IViolation, Set<IMarker>>();
		descriptionFactory = new DescriptionFactory();
	}

	@Override
	public void added(IViolation element) {
		if (!marks.containsKey(element)){
			searchAndMarkViolation(element);
		}
	}

	@Override
	public void deleted(IViolation element) {
		if (marks.containsKey(element)){
			Set<IMarker> listOfMarks = marks.get(element);
			for (IMarker mark : listOfMarks) {
				MarkingUtilities.deleteMarker(mark);
			}
			marks.remove(element);
		}
	}

	private void searchAndMarkViolation(IViolation element) {
		if (element.getSourceElement() != null){
			CodeElementFinder.startSearch(element.getSourceElement(), getRelatedProject(), new CodeElementMarker(ViolationManager.SOURCE, createSourceViolationDescription(element), element));
		}
		if (element.getTargetElement() != null){
			CodeElementFinder.startSearch(element.getTargetElement(), getRelatedProject(), new CodeElementMarker(ViolationManager.TARGET, createTargetViolationDescription(element), element));
		}	
	}

	public void saveMarker(IViolation violation, IMarker mark) {
		if (mark != null){
			if (marks.containsKey(violation)){
				Set<IMarker> listOfMarks = marks.get(violation);
				listOfMarks.add(mark);
			}else{
				Set<IMarker> listOfMarks = new HashSet<IMarker>();
				listOfMarks.add(mark);
				marks.put(violation, listOfMarks);
			}
		}
	}

	private String createTargetViolationDescription(IViolation element) {
		return descriptionFactory.getDescription(element);
	}

	private String createSourceViolationDescription(IViolation element) {
		return descriptionFactory.getDescription(element);
	}
	
	/**
	 * Implementation of ICodeElementFoundProcessor
	 * for marking CodeElements of IViolations
	 * 
	 * @author 
	 */
	private class CodeElementMarker implements ICodeElementFoundProcessor{

		private int type;
		private String description;
		private IViolation violation;

		public CodeElementMarker(int type, String description, IViolation violation){
			this.type = type;
			this.description = description;
			this.violation = violation;
		}

		@Override
		public void processFoundCodeElement(IMember member) {
			int priority = getSeverity();
			IMarker marker = MarkingUtilities.markIMember(member, description, priority);
			saveMarker(violation, marker);
		}

		@Override
		public void processFoundCodeElement(IMember member, int lineNr) {
			IMarker marker = MarkingUtilities.markIStatement(member, description, lineNr, getSeverity());
			saveMarker(violation, marker);
		}

		@Override
		public void noMatchFound(ICodeElement codeElement) {
			IMarker marker = MarkingUtilities.markIProject(getRelatedProject(), description, getSeverity());
			saveMarker(violation, marker);
		}

		private int getSeverity() {
			int severity = IMarker.PRIORITY_LOW;
			switch (type){
			case ViolationManager.SOURCE:
				severity = IMarker.PRIORITY_HIGH;
				break;
			case ViolationManager.TARGET:
				severity = IMarker.PRIORITY_LOW;
				break;
			}
			return severity;
		}
	}

}

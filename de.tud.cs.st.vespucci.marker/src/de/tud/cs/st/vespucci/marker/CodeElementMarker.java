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
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IViolation;

public class CodeElementMarker implements ICodeElementFoundProcessor {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.marker";
	
	private static HashMap<IViolation, List<IMarker>> markers = new HashMap<IViolation, List<IMarker>>();
	
	protected static IMarker markIStatement(IMember member, String description, int lineNumber, int priority){
		if ((lineNumber > -1) && (member.getResource() != null)){
			IProject project = member.getJavaProject().getProject();
			IFile file = project.getFile(member.getResource().getProjectRelativePath());
			
			return addMarker(file, description, lineNumber, priority);
		}
		return null;
	}
	
	protected static IMarker markIMember(IMember member, String description, int priority) {
		if (member.getResource() != null){
			try {
				IProject project = member.getJavaProject().getProject();
				IFile file = project.getFile(member.getResource().getProjectRelativePath());
				
				return addMarker(file, description, member.getSourceRange().getOffset(), member.getSourceRange().getOffset(), priority);	
				
			} catch (JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
		return null;	
	}
	
	/**
	 * Add a Marker (IMarker) to a given IFile
	 * @param file File to create the marker on
	 * @param message message shown in the ProblemsView
	 * @param lineNumber line which should be marked
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 * @return the created marker if it was possible to create one, otherwise null will be returned
	 */
	private static IMarker addMarker(IFile file, String message, int lineNumber, int severity) {
		try {
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			marker.setAttribute(IMarker.TRANSIENT, true);
			return marker;
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return null;
	}
	
	/**
	 * Add a Marker (IMarker) to a given IFile
	 * @param file File to create the marker on
	 * @param message message shown in the ProblemsView
	 * @param startPosition start position of the marker relative to the beginning of the file
	 * @param endPosition end position of the marker relative to the beginning of the file
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 * @return the created marker if it was possible to create one, otherwise null will be returned
	 */
	private static IMarker addMarker(IFile file, String message, int startPositon, int endPosition, int severity) {
		try {
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.CHAR_START, startPositon);
			marker.setAttribute(IMarker.CHAR_END, endPosition);
			marker.setAttribute(IMarker.TRANSIENT, true);
			return marker;
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return null;
	}
	
	protected static void deleteAllMarkers(){
		for (IViolation violation : markers.keySet()) {
			unmarkIViolation(violation);
		}
	}


	private boolean sourceElement;
	private String description; 
	private IViolation violation;
	
	public CodeElementMarker(boolean sourceElement, String description, IViolation violation) {
		this.sourceElement = sourceElement;
		this.description = description;
		this.violation = violation;
	}
	
	@Override
	public void processFoundCodeElement(IMember member) {
		int priority = IMarker.PRIORITY_LOW;
		if (sourceElement){
			priority = IMarker.PRIORITY_HIGH;
		}
		IMarker marker = markIMember(member, description, priority);
		
		saveMarker(marker);
	}

	private void saveMarker(IMarker marker) {
		if (marker != null){
			if (markers.containsKey(violation)){
				markers.get(violation).add(marker);
			}else{
				List<IMarker> markerList = new LinkedList<IMarker>();
				markerList.add(marker);
				markers.put(violation, markerList);
			}
		}
	}

	@Override
	public void processFoundCodeElement(IMember member, int lineNr) {
		int priority = IMarker.PRIORITY_LOW;
		if (sourceElement){
			priority = IMarker.PRIORITY_HIGH;
		}
		IMarker marker = markIStatement(member, description, lineNr, priority);
		
		saveMarker(marker);
	}

	@Override
	public void noMatchFound(ICodeElement codeElement) {

	}

	public static void unmarkIViolation(IViolation violation) {
		for (Entry<IViolation, List<IMarker>> entry : markers.entrySet()) {
			if (entry.getKey().equals(violation)){
				for (IMarker marker : markers.get(violation)) {
					try {
						marker.delete();
					} catch (CoreException e) {
						final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
						StatusManager.getManager().handle(is, StatusManager.LOG);
					}
				}
			}
		}
	}
}

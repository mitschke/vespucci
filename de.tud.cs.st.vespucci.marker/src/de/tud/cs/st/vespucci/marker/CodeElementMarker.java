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

import java.util.HashSet;
import java.util.Set;

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

public class CodeElementMarker implements ICodeElementFoundProcessor<String> {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.marker";
		
	private static Set<IMarker> markers = new HashSet<IMarker>();

	protected static void markIStatement(IMember member, String description, int lineNumber, IProject project){
		if ((lineNumber > -1) && (member.getResource() != null)){
			IFile file = project.getFile(member.getResource().getProjectRelativePath());
			
			addMarker(file, description, lineNumber, IMarker.PRIORITY_HIGH);
		}
	}
	
	protected static void markIMember(IMember member, String description, IProject project) {
		if (member.getResource() != null){
			try {
				IFile file = project.getFile(member.getResource().getProjectRelativePath());
				
				addMarker(file, description, member.getSourceRange().getOffset(), member.getSourceRange().getOffset(), IMarker.PRIORITY_HIGH);	
				
			} catch (JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}	
	}
	
	/**
	 * Add a Marker (IMarker) to a given IFile
	 * @param file File to create the marker on
	 * @param message message shown in the ProblemsView
	 * @param lineNumber line which should be marked
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 */
	private static void addMarker(IFile file, String message, int lineNumber, int severity) {
		try {
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			marker.setAttribute(IMarker.TRANSIENT, true);
			markers.add(marker);
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}
	
	/**
	 * Add a Marker (IMarker) to a given IFile
	 * @param file File to create the marker on
	 * @param message message shown in the ProblemsView
	 * @param startPosition start position of the marker relative to the beginning of the file
	 * @param endPosition end position of the marker relative to the beginning of the file
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 */
	private static void addMarker(IFile file, String message, int startPositon, int endPosition, int severity) {
		try {
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.CHAR_START, startPositon);
			marker.setAttribute(IMarker.CHAR_END, endPosition);
			marker.setAttribute(IMarker.TRANSIENT, true);
			markers.add(marker);
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}
	
	protected static void deleteAllMarkers(){
		for (IMarker marker : markers){
			try {
				marker.delete();
			} catch (CoreException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
	}

	@Override
	public void processFoundCodeElement(IMember member, String value, IProject project) {
		markIMember(member, value, project);
	}

	@Override
	public void processFoundCodeElement(IMember member, String value,
			int lineNr, IProject project) {
		markIStatement(member, value, lineNr, project);
		
	}
}

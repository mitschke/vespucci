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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Provide facilities to mark elements
 * 
 * @author 
 */
public class MarkingUtilities {

	/**
	 * Mark an line in a given IMember
	 * 
	 * @param member Member where a line should be marked
	 * @param message message shown in the ProblemsView
	 * @param lineNumber lineNumber line which should be marked
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 * @return created Marker or null if the resource of given IMember is null
	 */
	protected static IMarker markIStatement(IMember member, String message, int lineNumber, int severity){
		if ((member.getResource() != null)&&(!member.isBinary())){
			IProject project = member.getJavaProject().getProject();
			IFile file = project.getFile(member.getResource().getProjectRelativePath());
			
			return addMarker(file, message, lineNumber, severity);
		}
		return null;
	}
	
	/**
	 * Mark an line in a given IProject
	 * 
	 * @param project IProject where a line should be marked
	 * @param message message shown in the ProblemsView
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 * @return created Marker or null if the resource of given IMember is null
	 */
	protected static IMarker markIProject(IProject project, String message, int severity){
		if (project != null){
			return addMarker(project, message, 0, severity);
		}
		return null;
	}
	
	/**
	 * Mark a given IMember
	 * 
	 * @param member Member which should marked
	 * @param message message shown in the ProblemsView
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 * @return created Marker or null if the resource of given IMember is null or resource is a binary
	 */
	protected static IMarker markIMember(IMember member, String message, int severity) {
		if ((member.getResource() != null)&&(!member.isBinary())){			
			try {
				IProject project = member.getJavaProject().getProject();
				IFile file = project.getFile(member.getResource().getProjectRelativePath());
				int offset = member.getNameRange().getOffset();
				return addMarker(file, message, offset, offset, severity);
			} catch (JavaModelException e) {
				Marker.processException(e);
			}
		}
		return null;	
	}
	
	/**
	 * Mark a given IFile
	 * 
	 * @param file IFile which should marked
	 * @param message message shown in the ProblemsView
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 * @return created Marker or null if the resource of given IMember is null or resource is a binary
	 */
	protected static IMarker markIFile(IFile file, String message, int severity){
		return addMarker(file, message, 0, severity);
	}
	
	/**
	 * Delete a given IMember
	 */
	protected static void deleteMarker(IMarker marker){
		try {
			marker.delete();
		} catch (CoreException e) {
			Marker.processException(e);
		}
	}

	/**
	 * Add a Marker (IMarker) to a given IProject
	 * @param project IProject to create the marker on
	 * @param message message shown in the ProblemsView
	 * @param lineNumber line which should be marked
	 * @param severity Severity of the new Marker (@see IMarker) example: IMarker.IMarker.PRIORITY_HIGH
	 * @return the created marker if it was possible to create one, otherwise null will be returned
	 */
	private static IMarker addMarker(IProject project, String message, int lineNumber, int severity) {
		try {
			IMarker marker = project.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			marker.setAttribute(IMarker.TRANSIENT, true);
			return marker;
		}
		catch (CoreException e) {
			Marker.processException(e);
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
			Marker.processException(e);
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
			Marker.processException(e);
		}
		return null;
	}
}

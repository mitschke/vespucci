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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.interfaces.IMethodElement;
import de.tud.cs.st.vespucci.interfaces.ISourceClass;
import de.tud.cs.st.vespucci.interfaces.ISourceCodeElement;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationReport;

public class Marker implements IResultProcessor {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.mockreturnprocessor";
	
	// TODO: remove
	private boolean underline = false;
	
	private IProject project;
	private Set<IViolation> violations;
	
	private static Set<IMarker> markers = new HashSet<IMarker>();

	@Override
	public void processResult(Object result, IProject project) {
		this.project = project;
		this.violations = Util.adapt(result, IViolationReport.class).getViolations();
		if (violations != null){
			markViolations();
		}
	}

	private void markViolations() {
		for (IViolation violation : violations) {
			markISourceCodeElement(violation.getSourceElement(), violation.getDescription());
			markISourceCodeElement(violation.getTargetElement(), violation.getDescription());
		}
	}

	private void markISourceCodeElement(ISourceCodeElement sourceElement, String violationMessage) {
		SearchPattern searchPattern;
		
		//Set default SearchScope
		IJavaSearchScope javaSearchScope = SearchEngine.createWorkspaceScope();
		
		//Try to get better SearchScope
	    try {
	    	IJavaProject javaProject= JavaCore.create(project);
	    	IPackageFragmentRoot[] packageFragmentRoots = javaProject.getPackageFragmentRoots();
	    	
	    	List<IJavaElement> packages = new LinkedList<IJavaElement>();
	    	for (IPackageFragmentRoot packageFragmentRoot : packageFragmentRoots) {
				for (IJavaElement javaElement : packageFragmentRoot.getChildren()) {
					if (javaElement instanceof IPackageFragment){
						IPackageFragment candidatePackage = (IPackageFragment) javaElement;
						if (candidatePackage.getElementName().equals(sourceElement.getPackageIdentifier())){
							packages.add(candidatePackage);
						}
					}
				}
			}

	    	
	    	IJavaElement[] javaElements = new IJavaElement[packages.size()];
	    	for (int i = 0; i < packages.size(); i++) {
				javaElements[i] = packages.get(i);
			}
			javaSearchScope = SearchEngine.createJavaSearchScope(javaElements);
		} catch (JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	    
		if (sourceElement instanceof IMethodElement){
			IMethodElement methodElement = (IMethodElement) sourceElement;
			
			searchPattern = SearchPattern.createPattern(methodElement.getMethodName(), IJavaSearchConstants.METHOD, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			
			
		}else if (sourceElement instanceof ISourceClass){
			ISourceClass methodElement = (ISourceClass) sourceElement;
			
			searchPattern = SearchPattern.createPattern(methodElement.getSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
		}else{
			searchPattern = SearchPattern.createPattern(sourceElement.getSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
		}

		search(searchPattern, javaSearchScope, sourceElement, violationMessage);
		
	}

	private void search(SearchPattern searchPattern, IJavaSearchScope javaSearchScope, final ISourceCodeElement sourceElement, final String string) {
		
		SearchRequestor requestor = new SearchRequestor() {
			
			@Override
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				markFoundMatch(match, sourceElement, string);
			}
		};
		
		
	    // Search
	    SearchEngine searchEngine = new SearchEngine();
	    try {
			searchEngine.search(searchPattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, javaSearchScope, requestor, null);
		} catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	protected void markFoundMatch(SearchMatch match, ISourceCodeElement sourceElement, String violationMessage) {
		if ((match.getElement() instanceof IType) && (sourceElement instanceof ISourceClass)){
			markIMember((IMember) match.getElement(), violationMessage);
			
		}else if ((match.getElement() instanceof IMethod) && (sourceElement instanceof IMethodElement)){
			
			IMethod m = (IMethod) match.getElement();
			IMethodElement me = (IMethodElement) sourceElement;
			
			
			//is the returntype correct?
			try {
				if (m.getReturnType().equals(me.getReturnType())){
					String[] mParaTypes = m.getParameterTypes();
					String[] meParaTypes = me.getListParamTypes();
					
					if (meParaTypes.length == mParaTypes.length){
						boolean equalParameterTypes = true;
						for (int i = 0; i < mParaTypes.length; i++){
							if (!meParaTypes[i].equals(mParaTypes[i])){
								equalParameterTypes = false;
							}
						}
						if (equalParameterTypes){
							markIMember((IMember) match.getElement(), violationMessage);		
						}
					}
				}
			} catch (JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
			
			
		}else if ((match.getElement() instanceof IType) && (sourceElement instanceof ISourceCodeElement)){
			IType type = (IType) match.getElement();
			ISourceCodeElement sourceCodeElement = (ISourceCodeElement) sourceElement;
			
			if (sourceCodeElement.getLineNumber() != -1){
				IFile file = project.getFile(type.getResource().getProjectRelativePath());
				addMarker(file, violationMessage, sourceCodeElement.getLineNumber() , IMarker.PRIORITY_HIGH);	
			}
			
		}
	}

	protected void markIMember(IMember member, String description) {
		if (member.getResource() != null){
			try {
				int offSet = member.getSourceRange().getOffset();
				int length = member.getSourceRange().getLength();
				IFile file = project.getFile(member.getResource().getProjectRelativePath());
				int lineNumber = getLineNumber(offSet, member);
				if (underline){
					addMarker(file, description, offSet, offSet + length, IMarker.PRIORITY_HIGH);
				}else{
					addMarker(file, description, lineNumber, IMarker.PRIORITY_HIGH);	
				}				
			} catch (JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}	
	}

	private void addMarker(IFile file, String message, int start, int end, int severity) {
		try {
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.CHAR_START, start);
			marker.setAttribute(IMarker.CHAR_END, end);
			marker.setAttribute(IMarker.TRANSIENT, true);
			markers.add(marker);
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}
	
	private void addMarker(IFile file, String message, int lineNumber, int severity) {
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

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IViolationReport.class.equals(resultClass);
	}

	@Override
	public void cleanUp() {
		for (IMarker marker : markers){
			try {
				marker.delete();
			} catch (CoreException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
	}
	
	private int getLineNumber(int offset, IMember type){
		String source;
		int lineNumber= 1;
		try {
			source = type.getCompilationUnit().getSource();
			for (int i= 0; i < offset; i++){
				//TODO: must be changed. doesn't work on all maschines
				if (source.charAt(i) == Character.LINE_SEPARATOR){
			    	lineNumber++;
			    }
			}   
		} catch (JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

 
		return lineNumber;
	}


}

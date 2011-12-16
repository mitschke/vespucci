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

import java.util.LinkedList;
import java.util.List;

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

import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IStatement;

public class CodeElementFinder {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.marker";
	
	protected static void startSearchOfISourceCodeElement(ICodeElement sourceElement, String violationMessage, IProject project) {
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
	    
	    // create searchPattern specialized for each kind of element
	    
		if (sourceElement instanceof ICodeElement){
			ICodeElement methodElement = (ICodeElement) sourceElement;

			searchPattern = SearchPattern.createPattern(methodElement.getSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);		

			search(searchPattern, javaSearchScope, sourceElement, violationMessage, project);
			
		}
	
	}
	
	private static void search(SearchPattern searchPattern, IJavaSearchScope javaSearchScope, final ICodeElement sourceElement, final String string, final IProject project) {
		
		SearchRequestor requestor = new SearchRequestor() {
			
			@Override
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				foundMatch(match, sourceElement, string, project);
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

	protected static void foundMatch(SearchMatch match, ICodeElement sourceElement, String violationMessage, IProject project) {
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IClassDeclaration)){
			
			CodeElementMarker.markIMember((IMember) match.getElement(), violationMessage, project);
			
		}else if ((match.getElement() instanceof IType) && (sourceElement instanceof IMethodDeclaration)){
			
			IType m = (IType) match.getElement();
			IMethodDeclaration me = (IMethodDeclaration) sourceElement;
						
			SearchPattern searchPattern = SearchPattern.createPattern(me.getMethodName(), IJavaSearchConstants.METHOD, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			
			IJavaElement[] je = new IJavaElement[1];
			je[0] = m;
			IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope(je);
			
			search(searchPattern, javaSearchScope, sourceElement, violationMessage, project);
			
			
		}else if ((match.getElement() instanceof IMethod) && (sourceElement instanceof IMethodDeclaration)){
			
			IMethod m = (IMethod) match.getElement();
			IMethodDeclaration me = (IMethodDeclaration) sourceElement;
			
			
			//check weather the method signature is correct
			try {
				if (m.getReturnType().equals(me.getReturnTypeQualifier())){
					String[] mParaTypes = m.getParameterTypes();
					String[] meParaTypes = me.getParameterTypeQualifiers();
					
					if (meParaTypes.length == mParaTypes.length){
						boolean equalParameterTypes = true;
						for (int i = 0; i < mParaTypes.length; i++){
							if (!meParaTypes[i].equals(mParaTypes[i])){
								equalParameterTypes = false;
							}
						}
						if (equalParameterTypes){
							CodeElementMarker.markIMember((IMember) match.getElement(), violationMessage, project);		
						}
					}
				}
			} catch (JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
			
		}else if ((match.getElement() instanceof IType) && (sourceElement instanceof IStatement)){
			
			IType type = (IType) match.getElement();
			IStatement sourceCodeElement = (IStatement) sourceElement;
			
			//TODO:
			if (sourceCodeElement.getLineNumber() != -1){
				IFile file = project.getFile(type.getResource().getProjectRelativePath());
				//addMarker(file, violationMessage, sourceCodeElement.getLineNumber() , IMarker.PRIORITY_HIGH);	
			}
			
		}
	}
	
}

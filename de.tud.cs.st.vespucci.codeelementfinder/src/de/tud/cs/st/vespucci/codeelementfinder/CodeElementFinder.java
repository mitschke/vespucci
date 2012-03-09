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
 *     Universitiät Darmstadt nor the names of its contributors may be used to
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
package de.tud.cs.st.vespucci.codeelementfinder;

import java.util.List;
import java.util.Stack;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
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
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

public class CodeElementFinder {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";

	//private static WeakHashMap<ICodeElement, IMember> cache = new WeakHashMap<ICodeElement, IMember>();

	private ICodeElement codeElement;
	private IProject project;
	private ICodeElementFoundProcessor codeElementFoundProcessor;
	private Stack<ICodeElement> stack = new Stack<ICodeElement>();
	
	private boolean found = false;
	private IMember foundMatch = null;

	public CodeElementFinder(ICodeElement codeElement, IProject project, ICodeElementFoundProcessor codeElementFoundProcessor){
		this.codeElement = codeElement;
		this.project = project;
		this.codeElementFoundProcessor = codeElementFoundProcessor;

		initSearchTries();
	}

	private void initSearchTries() {
		stack.clear();
		List<ICodeElement> searchTries = Util.createSearchTryStack(codeElement);
		for (int i = searchTries.size()-1; i >= 0; i--){
			stack.push(searchTries.get(i));
		}
	}

	public void startSearch(){
		tryNext();
	}

	private void next(){
		if (found){
			//put foundMatch in WeakHashMap
			codeElementFoundProcessor.processFoundCodeElement(foundMatch);
		}else{
			tryNext();
		}
	}

	private void tryNext(){
		if (stack.isEmpty()){
			codeElementFoundProcessor.noMatchFound(codeElement);
		}else{
			ICodeElement nextTry = stack.pop();
			SearchPattern searchPattern = SearchPattern.createPattern(Util.createStringPattern(nextTry), Util.createSearchFor(nextTry), IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			search(searchPattern, SearchEngine.createWorkspaceScope(), nextTry, project, codeElementFoundProcessor);
		}
	}

	public static void startSearch(ICodeElement sourceElement, IProject project, ICodeElementFoundProcessor processor){	
		CodeElementFinder cef = new CodeElementFinder(sourceElement, project, processor);
		cef.startSearch();
	}
	
	private void search(SearchPattern searchPattern, final IJavaSearchScope javaSearchScope, final ICodeElement sourceElement, final IProject project, final ICodeElementFoundProcessor processor) {

		SearchRequestor requestor = new SearchRequestor() {
			@Override
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				if (!found){
					foundMatch(match, sourceElement, javaSearchScope, processor);
				}
			}

			@Override
			public void endReporting(){
				next();
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

	private void foundMatch(SearchMatch match, ICodeElement sourceElement, IJavaSearchScope searchScope, ICodeElementFoundProcessor processor) {
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IClassDeclaration)){	
			foundMatch((IType) match.getElement(), (IClassDeclaration) sourceElement, processor);
		}	
		if ((match.getElement() instanceof IMethod) && (sourceElement instanceof IMethodDeclaration)){
			foundMatch((IMethod) match.getElement(), (IMethodDeclaration) sourceElement, processor);
		}
		if ((match.getElement() instanceof IField) && (sourceElement instanceof IFieldDeclaration)){
			foundMatch((IField) match.getElement(), (IFieldDeclaration) sourceElement, processor);
		}
	}

	private void foundMatch(IType type, IClassDeclaration classDeclaration, ICodeElementFoundProcessor processor) {
		if (classDeclaration.getTypeQualifier() != null){
			if (type.getKey().equals(classDeclaration.getTypeQualifier())){
				foundMatch = type;
				found = true;
			}
		}else{
			foundMatch = type;
			found = true;
		}
	}

	private void foundMatch(IField field, IFieldDeclaration fieldDeclaration, ICodeElementFoundProcessor processor) {
		try {
			if (fieldDeclaration.getTypeQualifier().equals(Util.createTypQualifier(field.getTypeSignature(), field.getDeclaringType()))){
				foundMatch = field;
				found = true;
			}
		} catch (JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	private void foundMatch(IMethod method, IMethodDeclaration methodDeclaration, ICodeElementFoundProcessor processor) {
		try {
			IType declaringType = method.getDeclaringType();
			
			// check if the returnType is the expected
			if (methodDeclaration.getReturnTypeQualifier().equals(Util.createTypQualifier(method.getReturnType(), declaringType))){
				boolean equal = true;

				// check if all parameterTypes are equal the expected
				String[] parameterTypes = method.getParameterTypes();
				String[] expectedParameterTypes = methodDeclaration.getParameterTypeQualifiers();

				if(expectedParameterTypes.length != parameterTypes.length)
					return;
				for (int i = 0; i < parameterTypes.length; i++){
					if (!expectedParameterTypes[i].equals(Util.createTypQualifier(parameterTypes[i], declaringType))){
						equal = false;
						break;
					}
				}

				if (equal){
					foundMatch = method;
					found = true;
				}
			}
		} catch (JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}
}
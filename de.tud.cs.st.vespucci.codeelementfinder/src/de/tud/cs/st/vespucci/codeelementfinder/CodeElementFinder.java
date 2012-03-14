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

import java.util.Stack;
import java.util.WeakHashMap;

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
import de.tud.cs.st.vespucci.interfaces.IStatement;

/**
 * Class that is able to find ICodeElements in workspace
 * 
 * @author 
 */
public class CodeElementFinder {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";

	protected static void processException(Exception e){
		final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}

	private static WeakHashMap<ICodeElement, IMember> cache = new WeakHashMap<ICodeElement, IMember>();

	private ICodeElement codeElement;
	private ICodeElementFoundProcessor codeElementFoundProcessor;
	private Stack<ICodeElement> stack = new Stack<ICodeElement>();
	
	private ICodeElement actualSearchItem;
	private Boolean found = false;
	private IMember foundMatch = null;

	/**
	 * Search an ICodeElement
	 * 
	 * @param sourceElement ICodeElement looking for
	 * @param project IProject that looking in
	 * @param processor Processor that declares what should be done when ICodeElement is found or not found
	 */
	public static void startSearch(ICodeElement sourceElement, IProject project, ICodeElementFoundProcessor processor){	
		CodeElementFinder cef = new CodeElementFinder(sourceElement, project, processor);
		cef.nextSearchStep();
	}

	private CodeElementFinder(ICodeElement codeElement, IProject project, ICodeElementFoundProcessor codeElementFoundProcessor){
		this.codeElement = codeElement;
		this.codeElementFoundProcessor = codeElementFoundProcessor;

		stack = Util.createSearchTryStack(codeElement);
		
		// checks if CodeElement was already found before
		if (cache.containsKey(codeElement)){
			IMember match = cache.get(codeElement);
			if (match.exists()){
				found = true;
				foundMatch = match;
			}
		}
	}

	/**
	 * Process the next step in the search process
	 */
	private void nextSearchStep(){
		if (found){
			cache.put(codeElement, foundMatch);
			if (actualSearchItem instanceof IStatement){
				IStatement statement = (IStatement) codeElement;
				codeElementFoundProcessor.processFoundCodeElement(foundMatch, statement.getLineNumber());
			}else{
				codeElementFoundProcessor.processFoundCodeElement(foundMatch);
			}
		}else{
			if (stack.isEmpty()){
				codeElementFoundProcessor.noMatchFound(codeElement);
			}else{
				actualSearchItem = stack.pop();
				searchItem();
			}
		}
	}

	/**
	 * Start searching
	 * 
	 * @param codeElement ICodeElement searched for
	 */
	private void searchItem() {

		SearchRequestor requestor = new CodeElementSearchRequestor(actualSearchItem);

		SearchEngine searchEngine = new SearchEngine();
		try {
			SearchPattern searchPattern = SearchPattern.createPattern(Util.createStringPattern(actualSearchItem), Util.createSearchFor(actualSearchItem), IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			IJavaSearchScope javaSearchScope = SearchEngine.createWorkspaceScope();
			searchEngine.search(searchPattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, javaSearchScope, requestor, null);
		} catch (CoreException e) {
			processException(e);
		}
	}

	/**
	 * SearchRequestor for searching ICodeElements
	 * 
	 * If an ICodeElement was found the SearchRequester set<br>
	 * <code>found = true</code> and <code>foundMatch = </code> Match which was found 
	 * 
	 * 
	 * @author 
	 */
	class CodeElementSearchRequestor extends SearchRequestor {

		private ICodeElement codeElement;

		public CodeElementSearchRequestor(ICodeElement codeElement) {
			this.codeElement = codeElement;
		}

		@Override
		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			if (!found){
				foundMatch(match, codeElement);
			}
		}

		@Override
		public void endReporting(){
			nextSearchStep();
		}
		
		/**
		 * Checks if an found match is that what were looking for
		 * 
		 * @param match Found match
		 * @param sourceElement ICodeElement looking for
		 */
		private void foundMatch(SearchMatch match, ICodeElement sourceElement) {
			if ((match.getElement() instanceof IType) && (sourceElement instanceof IClassDeclaration)){	
				foundMatch((IType) match.getElement(), (IClassDeclaration) sourceElement);
			}
			if ((match.getElement() instanceof IType) && (sourceElement instanceof IStatement)){	
				foundMatch((IType) match.getElement(), (IStatement) sourceElement);
			}	
			if ((match.getElement() instanceof IMethod) && (sourceElement instanceof IMethodDeclaration)){
				foundMatch((IMethod) match.getElement(), (IMethodDeclaration) sourceElement);
			}
			if ((match.getElement() instanceof IField) && (sourceElement instanceof IFieldDeclaration)){
				foundMatch((IField) match.getElement(), (IFieldDeclaration) sourceElement);
			}
		}

		/**
		 * Checks if an found match is the IClassDeclaration we were looking for
		 * 
		 * @param match Found match
		 * @param classDeclaration IClassDeclaration looking for
		 */
		private void foundMatch(IType type, IClassDeclaration classDeclaration) {
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

		/**
		 * Checks if an found match is the IStatement we were looking for
		 * 
		 * @param match Found match
		 * @param classDeclaration IClassDeclaration looking for
		 */
		private void foundMatch(IType type, IStatement sourceElement) {
			// In case of searching for an IStatement there is no possibility to checker
			// if the found match is the correct. A found match is always process as a correct match
			foundMatch = type;
			found = true;
		}
		
		/**
		 * Checks if an found match is the IFieldDeclaration we were looking for
		 * 
		 * @param match Found match
		 * @param classDeclaration IFieldDeclaration looking for
		 */
		private void foundMatch(IField field, IFieldDeclaration fieldDeclaration) {
			try {
				if (fieldDeclaration.getTypeQualifier().equals(Util.createTypQualifier(field.getTypeSignature(), field.getDeclaringType()))){
					foundMatch = field;
					found = true;
				}
			} catch (JavaModelException e) {
				processException(e);
			}
		}

		/**
		 * Checks if an found match is the IMethodDeclaration we were looking for
		 * 
		 * @param match Found match
		 * @param classDeclaration IMethodDeclaration looking for
		 */
		private void foundMatch(IMethod method, IMethodDeclaration methodDeclaration) {
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
				processException(e);
			}
		}
	};
}
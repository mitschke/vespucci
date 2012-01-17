package de.tud.cs.st.vespucci.codeelementfinder;
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


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IField;
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
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.codeelementfinder.extra.IComplexCodeElement;
import de.tud.cs.st.vespucci.codeelementfinder.extra.spi.ComplexCodeElement;
import de.tud.cs.st.vespucci.information.interfaces.spi.ClassDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.FieldDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.MethodDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.Statement;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IStatement;

public class CodeElementFinder {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";

	public static void startSearch(ICodeElement sourceElement, IProject project, ICodeElementFoundProcessor processor){
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
	    
	    // start initial search

		searchPattern = SearchPattern.createPattern(sourceElement.getSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);		

		search(searchPattern, javaSearchScope, sourceElement, project, processor);

	}

	private static void search(SearchPattern searchPattern, final IJavaSearchScope javaSearchScope, final ICodeElement sourceElement, final IProject project, final ICodeElementFoundProcessor processor) {
		
		SearchRequestor requestor = new SearchRequestor() {
			private boolean sucess = false;
			
			@Override
			public void acceptSearchMatch(SearchMatch match) throws CoreException {
				if (sucess){
					foundMatch(match, sourceElement, javaSearchScope, processor);
				}else{
					sucess = foundMatch(match, sourceElement, javaSearchScope, processor); 
				}
				
			}		
			
			@Override
			public void endReporting(){
				if (!sucess){
					notfoundMatch(sourceElement, project, processor);
				}
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


	private static boolean foundMatch(SearchMatch match, ICodeElement sourceElement, IJavaSearchScope searchScope, ICodeElementFoundProcessor processor) {
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IComplexCodeElement)){
			return foundMatch((IType) match.getElement(), (IComplexCodeElement) sourceElement, searchScope, processor);
		}
		// Find a class declaration and we were looking for it
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IClassDeclaration)){	
			return foundMatch((IType) match.getElement(), (IClassDeclaration) sourceElement, processor);
		}	
		// Find a class declaration and we were looking for a IMethodDeclaration
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IMethodDeclaration)){
			return foundMatch((IType) match.getElement(), (IMethodDeclaration) sourceElement, processor);
		}
		// Find a class declaration and we were looking for a IFieldDeclaration
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IFieldDeclaration)){
			return foundMatch((IType) match.getElement(), (IFieldDeclaration) sourceElement, processor);
		}
		// Find a class declaration and we were looking for a IStatement
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IStatement)){
			return foundMatch((IType) match.getElement(), (IStatement) sourceElement, processor);
		}
		// Find a method and we were looking for a IMethodDeclaration
		if ((match.getElement() instanceof IMethod) && (sourceElement instanceof IMethodDeclaration)){
			return foundMatch((IMethod) match.getElement(), (IMethodDeclaration) sourceElement, processor);
		}
		// find a field declaration and we were looking for a IFieldDeclaration
		if ((match.getElement() instanceof IField) && (sourceElement instanceof IFieldDeclaration)){
			return foundMatch((IField) match.getElement(), (IFieldDeclaration) sourceElement, processor);
		}
		return false;
	}


	private static boolean foundMatch(IType type, IComplexCodeElement complexCodeElement, IJavaSearchScope searchScope, ICodeElementFoundProcessor processor) {
		
		complexCodeElement.setFoundPartInfos(complexCodeElement.getSimpleClassName(), searchScope);
		
		if (complexCodeElement.isWaitingAreaEmtpy()){
			// manipulate ICSCE
			
			ICodeElement temp = complexCodeElement.getSpecialElement();
			ICodeElement element = null;
			
			if (temp instanceof IClassDeclaration){
				IClassDeclaration t = (IClassDeclaration) temp;
				element = new ClassDeclaration(t.getPackageIdentifier(), complexCodeElement.getLastFoundSimpleClassName(), t.getTypeQualifier());
			}else
			if (temp instanceof IMethodDeclaration){
				IMethodDeclaration t = (IMethodDeclaration) temp;
				element = new MethodDeclaration(t.getPackageIdentifier(), complexCodeElement.getLastFoundSimpleClassName(), t.getMethodName(), t.getReturnTypeQualifier(), t.getParameterTypeQualifiers());
			}else
			if (temp instanceof IFieldDeclaration){
				IFieldDeclaration t = (IFieldDeclaration) temp;
				element = new FieldDeclaration(t.getPackageIdentifier(), complexCodeElement.getLastFoundSimpleClassName(), t.getFieldName(), t.getTypeQualifier());
			}else
			if (temp instanceof IStatement){
				IStatement t = (IStatement) temp;
				element = new Statement(t.getPackageIdentifier(), complexCodeElement.getLastFoundSimpleClassName(), t.getLineNumber());
			}
			
			if (element != null){
				SearchPattern searchPattern = SearchPattern.createPattern(complexCodeElement.getSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
				
				IJavaSearchScope newSearchScope = complexCodeElement.getSearchScopeOfLastFoundSimpleClassName();
				
				search(searchPattern, newSearchScope, element, type.getJavaProject().getProject(), processor);
				return true;
			}				
			
		}else{
			complexCodeElement.setSimpleClassName(complexCodeElement.popFromWaitingArea());
			
			IJavaElement[] je = new IJavaElement[1];
			je[0] = type;
			IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope(je);
			
			SearchPattern searchPattern = SearchPattern.createPattern(complexCodeElement.getSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			
			search(searchPattern, javaSearchScope, complexCodeElement, type.getJavaProject().getProject(), processor);
			return true;
		}
		return false;
	}

	private static boolean foundMatch(IType type, IClassDeclaration classDeclaration, ICodeElementFoundProcessor processor) {
		if (classDeclaration.getTypeQualifier() != null){
			if (type.getKey().equals(classDeclaration.getTypeQualifier())){
				processor.processFoundCodeElement(type);
				return true;
			}
		}else{
			processor.processFoundCodeElement(type);
			return true;
		}

		return false;
	}

	private static boolean foundMatch(IType type, IFieldDeclaration fieldDeclaration, ICodeElementFoundProcessor processor) {
		SearchPattern searchPattern = SearchPattern.createPattern(fieldDeclaration.getFieldName(), IJavaSearchConstants.FIELD, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
		
		IJavaElement[] je = new IJavaElement[1];
		je[0] = type;
		IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope(je);
		
		search(searchPattern, javaSearchScope, fieldDeclaration, type.getJavaProject().getProject(), processor);
		
		return true;
	}

	private static boolean foundMatch(IField field, IFieldDeclaration fieldDeclaration, ICodeElementFoundProcessor processor) {
		try {
			if (fieldDeclaration.getTypeQualifier().equals(Util.createTypQualifier(field.getTypeSignature(), field.getDeclaringType()))){
				processor.processFoundCodeElement(field);
				return true;
			}
		} catch (JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		
		// return false, if the match was not what we were searching for
		return false;
	}

	private static boolean foundMatch(IType type, IMethodDeclaration methodeDeclaration, ICodeElementFoundProcessor processor) {
		SearchPattern searchPattern = SearchPattern.createPattern(methodeDeclaration.getMethodName(), IJavaSearchConstants.METHOD, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
		
		IJavaElement[] je = new IJavaElement[1];
		je[0] = type;
		IJavaSearchScope javaSearchScope = SearchEngine.createJavaSearchScope(je);
		
		search(searchPattern, javaSearchScope, methodeDeclaration, type.getJavaProject().getProject(), processor);
		
		return true;
	}

	private static boolean foundMatch(IMethod method, IMethodDeclaration methodDeclaration, ICodeElementFoundProcessor processor) {
		try {
			IType declaringType = method.getDeclaringType();
			
			// check if the returnType is the expected
			if (methodDeclaration.getReturnTypeQualifier().equals(Util.createTypQualifier(method.getReturnType(), declaringType))){
				// check if all parameterTypes are equal the expected
				String[] parameterTypes = method.getParameterTypes();
				String[] expectedParameterTypes = methodDeclaration.getParameterTypeQualifiers();
				boolean equal = true;
				for (int i = 0; i < parameterTypes.length; i++){
					if (!expectedParameterTypes[i].equals(Util.createTypQualifier(parameterTypes[i], declaringType))){
						equal = false;
						break;
					}
				}
				if (equal){
					processor.processFoundCodeElement(method);
					return true;
				}
			}
		} catch (JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		
		
		// return false, if the match was not the right hit	
		return false;
	}

	private static boolean foundMatch(IType type, IStatement sourceCodeElement, ICodeElementFoundProcessor processor) {
		processor.processFoundCodeElement(type, sourceCodeElement.getLineNumber());
		return true;
	}


	private static void notfoundMatch(ICodeElement sourceElement, IProject project, ICodeElementFoundProcessor processor) {

		if (sourceElement instanceof IComplexCodeElement){
			IComplexCodeElement temp = (IComplexCodeElement) sourceElement;
			
			if (temp.alreadyFindSomePart()){
				if (temp.isWaitingAreaEmtpy()){
					processBadSearchResult(project, temp, processor);	
				}else {
					// add WaitingArea Element
					temp.setSimpleClassName(temp.getSimpleClassName() + "$" + temp.popFromWaitingArea());
					startSearch(temp, project, processor);
				}
			}else{
				if (temp.getSimpleClassName().contains("$")){
					temp.pushToWaitingArea(Util.getLastDollarSequence(temp.getSimpleClassName()));
					temp.setSimpleClassName(Util.removeLastDollarSequence(temp.getSimpleClassName()));
					
					startSearch(temp, project, processor);
				}else{
					processor.noMatchFound(sourceElement);
				}
			}
		}else{
			if (sourceElement.getSimpleClassName().contains("$")){
				//create instance of IComplexeCodeElement
				IComplexCodeElement temp = new ComplexCodeElement(sourceElement.getPackageIdentifier(),
																	Util.removeLastDollarSequence(sourceElement.getSimpleClassName()), 
																	sourceElement);
				temp.pushToWaitingArea(Util.getLastDollarSequence(sourceElement.getSimpleClassName()));	
				startSearch(temp, project, processor);
			}else{
				processor.noMatchFound(sourceElement);			}
		}
		
	}

	private static void processBadSearchResult(IProject project, IComplexCodeElement temp, ICodeElementFoundProcessor processor) {
		
		if ((temp.getLastFoundSimpleClassName() != null)&&(temp.getSearchScopeOfLastFoundSimpleClassName() != null)){
			IClassDeclaration te = new ClassDeclaration(temp.getPackageIdentifier(), temp.getLastFoundSimpleClassName(), null);
			SearchPattern searchPattern = SearchPattern.createPattern(temp.getLastFoundSimpleClassName(), IJavaSearchConstants.CLASS, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
			
			search(searchPattern, temp.getSearchScopeOfLastFoundSimpleClassName(), te, project, processor);
		}
	}

}

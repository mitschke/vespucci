package de.tud.cs.st.vespucci.codeelementfinder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IStatement;

/**
 * SearchRequestor for searching ICodeElements
 * 
 * @author Olav Lenz
 */
public class CodeElementSearchRequestor extends SearchRequestor {

	private ICodeElement codeElement;

	private Boolean found = false;
	private IMember foundMatch = null;

	private IAction finishSearchAction;

	/**
	 * 
	 * 
	 * @param codeElement CodeElement looking for
	 * @param finishSearchAction Action that is performed when the search process is finished
	 */
	public CodeElementSearchRequestor(ICodeElement codeElement, IAction finishSearchAction) {
		this.codeElement = codeElement;
		this.finishSearchAction = finishSearchAction;
	}

	@Override
	public void acceptSearchMatch(SearchMatch match) throws CoreException {
		if (!found){
			checkReturnedMatch(match, codeElement);
		}
	}

	@Override
	public void endReporting(){
		finishSearchAction.run();
	}

	/**
	 * Return weather the search process was successful
	 * Should be called only after the search process finished.
	 * 
	 * @return true if the search was successful otherwise false
	 */
	public boolean getSearchResult(){
		return found;
	}

	/**
	 * Return the found match if the search process was successful
	 * Should be called only after the search process finished.
	 * 
	 * @return The found match or null if no match was found
	 */
	public IMember getSearchMatch(){
		return foundMatch;
	}
	
	/**
	 * Checks if an found match is that what were looking for
	 * 
	 * @param match Found match
	 * @param sourceElement ICodeElement looking for
	 */
	private void checkReturnedMatch(SearchMatch match, ICodeElement sourceElement) {
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IClassDeclaration)){	
			checkReturnedMatch((IType) match.getElement(), (IClassDeclaration) sourceElement);
		}
		if ((match.getElement() instanceof IType) && (sourceElement instanceof IStatement)){	
			checkReturnedMatch((IType) match.getElement(), (IStatement) sourceElement);
		}	
		if ((match.getElement() instanceof IMethod) && (sourceElement instanceof IMethodDeclaration)){
			checkReturnedMatch((IMethod) match.getElement(), (IMethodDeclaration) sourceElement);
		}
		if ((match.getElement() instanceof IField) && (sourceElement instanceof IFieldDeclaration)){
			checkReturnedMatch((IField) match.getElement(), (IFieldDeclaration) sourceElement);
		}
	}

	/**
	 * Checks if an found match is the IClassDeclaration we were looking for
	 * 
	 * @param match Found match
	 * @param classDeclaration IClassDeclaration looking for
	 */
	private void checkReturnedMatch(IType type, IClassDeclaration classDeclaration) {
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
	private void checkReturnedMatch(IType type, IStatement sourceElement) {
		// In case of searching for an IStatement there is no possibility to checked
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
	private void checkReturnedMatch(IField field, IFieldDeclaration fieldDeclaration) {
		try {
			if (fieldDeclaration.getTypeQualifier().equals(Util.createTypQualifier(field.getTypeSignature(), field.getDeclaringType()))){
				foundMatch = field;
				found = true;
			}
		} catch (JavaModelException e) {
			CodeElementFinder.processException(e);
		}
	}

	/**
	 * Checks if an found match is the IMethodDeclaration we were looking for
	 * 
	 * @param match Found match
	 * @param classDeclaration IMethodDeclaration looking for
	 */
	private void checkReturnedMatch(IMethod method, IMethodDeclaration methodDeclaration) {
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
			CodeElementFinder.processException(e);
		}
	}
};

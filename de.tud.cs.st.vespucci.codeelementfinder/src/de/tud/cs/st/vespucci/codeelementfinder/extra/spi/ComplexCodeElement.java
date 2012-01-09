package de.tud.cs.st.vespucci.codeelementfinder.extra.spi;

import java.util.Stack;

import org.eclipse.jdt.core.search.IJavaSearchScope;

import de.tud.cs.st.vespucci.codeelementfinder.extra.IComplexCodeElement;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;

public class ComplexCodeElement implements IComplexCodeElement {

	private String packageIdentifier;
	private String simpleClassName;
	private ICodeElement specialCodeElement;
	private String lastFoundSimpleClassName;
	private IJavaSearchScope lastSearchScope;
	private Stack<String> waitingArea;

	
	public ComplexCodeElement(String packageIdentifier, String simpleClassName,
			ICodeElement specialCodeElement) {
		this.packageIdentifier = packageIdentifier;
		this.simpleClassName = simpleClassName;
		this.specialCodeElement = specialCodeElement;
		lastFoundSimpleClassName = null;
		lastSearchScope = null;
		waitingArea = new Stack<String>();
	}

	@Override
	public String getPackageIdentifier() {
		return packageIdentifier;
	}

	@Override
	public String getSimpleClassName() {
		return simpleClassName;
	}
	
	public void setSimpleClassName(String simpleClassName){
		this.simpleClassName = simpleClassName;
	}

	@Override
	public ICodeElement getSpecialElement() {
		return specialCodeElement;
	}

	@Override
	public void setFoundPartInfos(String keyWord,
			IJavaSearchScope searchScope) {
		this.lastFoundSimpleClassName = keyWord;
		this.lastSearchScope = searchScope;
	}

	@Override
	public String getLastFoundSimpleClassName() {
		return lastFoundSimpleClassName;
	}

	@Override
	public IJavaSearchScope getSearchScopeOfLastFoundSimpleClassName() {
		return lastSearchScope;
	}

	@Override
	public boolean alreadyFindSomePart() {
		return (lastFoundSimpleClassName != null && lastSearchScope != null);
	}

	@Override
	public void pushToWaitingArea(String string) {
		waitingArea.push(string);
	}

	@Override
	public String popFromWaitingArea() {
		return waitingArea.pop();
	}

	@Override
	public boolean isWaitingAreaEmtpy() {
		return waitingArea.isEmpty();
	}

}
